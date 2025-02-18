package bhaashik.servers.clients;

import bhaashik.servers.utils.JSchSSHUtils;
import bhaashik.servers.utils.RMIUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import bhaashik.server.dao.auth.model.domain.BhaashikRole;
import bhaashik.server.dto.auth.model.domain.*;
import bhaashik.server.dto.model.files.RemoteFile;
import bhaashik.server.dto.payload.TextFileUpload;
import bhaashik.server.dto.payload.UploadFileResponse;
import bhaashik.server.dto.tree.impl.RemoteFileNode;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import bhaashik.common.BhaashikSpringServerEndPoints;
import bhaashik.server.mapper.BhaashikMapperUtils;

//@Slf4j
@Component
public class BhaashikSpringRestClient {

    public static String SANCHAY_CONFIG_PATH;
    public static String SANCHAY_CONFIG_FILENAME = "bhaashik-client-config.txt";

    public static String SANCHAY_CLIENT_ANNOTATION_FOLDER;
    public static Properties SANCHAY_CONFIG_PROPERTIES;

    public static String AUTH_BASE_URL;
    public static String FILE_BASE_URL;

    private String username;
    private String password;

    private BhaashikUserDTO user;
    private List<BhaashikRole> roles;

    private String access_token;
    private String refresh_token;
    
    @Autowired
    private RestTemplate restTemplate;

    private ObjectMapper plainObjectMapper;
//    private ObjectMapper polymorphicObjectMapper;

//    @Autowired
    private ModelMapper modelMapper;

//    private final RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
    private CloseableHttpClient httpClient;

    public BhaashikSpringRestClient() throws IOException {
    }

    public void init() throws IOException {

        modelMapper = BhaashikSpringClientUtils.getModelMapperInstance();
        plainObjectMapper = BhaashikMapperUtils.getPlainObjectMapperInstance();
//        polymorphicObjectMapper = BhaashikMapperUtils.getPolymorphicObjectMapperInstance();

        SANCHAY_CONFIG_PATH = System.getProperty("user.dir") + "/" + SANCHAY_CONFIG_FILENAME;
        SANCHAY_CONFIG_PROPERTIES = BhaashikSpringClientUtils.loadPropertiesFile(SANCHAY_CONFIG_PATH);

        System.out.printf("Loaded properties file %s.", SANCHAY_CONFIG_PATH);
        System.out.printf("Bhaashik client annotation folder is: %s\n", SANCHAY_CONFIG_PROPERTIES.getProperty("CLIENT_ANNOTATION_FOLDER"));

        AUTH_BASE_URL = SANCHAY_CONFIG_PROPERTIES.getProperty("BASE_URL")
                + BhaashikSpringServerEndPoints.AUTH_BASE;
        FILE_BASE_URL = SANCHAY_CONFIG_PROPERTIES.getProperty("BASE_URL")
                + BhaashikSpringServerEndPoints.FILE_SERVER_BASE;

        try (CloseableHttpClient hclient = HttpClients.createDefault()) {
            httpClient = hclient;
        }
        for (HttpMessageConverter converter : restTemplate.getMessageConverters()) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setWriteAcceptCharset(false);
            }
        }
    }
    
    public ModelMapper getModelMapper()
    {
        return modelMapper;
    }

    public void setRestTemplate(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }
    
    public String getAccessToken()
    {
        return access_token;
    }
    
    public String getRefreshToken()
    {
        return refresh_token;
    }

    public String authenticateUser(String server, String username, String password) {
        if(server != null || !server.equals(""))
        {
            AUTH_BASE_URL = server + BhaashikSpringServerEndPoints.AUTH_BASE;
            FILE_BASE_URL = server + BhaashikSpringServerEndPoints.FILE_SERVER_BASE;            
        }
        
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.LOGIN;
        String requestBody = "username=" + username + "&" + "password=" + password;

        System.out.println("Authentical URL: " + authenticationURL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

//        String response = restTemplate.getForObject(url, String.class);
        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity, String.class);
//        String response = restTemplate.getForObject(url, entity, String.class);

//        ObjectMapper mapper = new ObjectMapper();

        try {
            // convert JSON string to `JsonNode`
            JsonNode node = plainObjectMapper.readTree(response.getBody());
//            JsonNode node = mapper.readTree(response.getBody());

            access_token = node.path("access_token").asText();
            refresh_token = node.path("refresh_token").asText();

        } catch (JsonProcessingException ex) {
            Logger.getLogger(BhaashikSpringRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Response: " + response);
        System.out.println("Access token: " + access_token);
        System.out.println("Refresh token: " + refresh_token);

        return access_token;
    }

//    @Scheduled(fixedRate = 50000)
    public String refreshToken() {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.TOKEN_REFRESH;
        String requestBody = "body";

        System.out.println("Authentical URL for token refresh: " + authenticationURL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + refresh_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

//        String response = restTemplate.getForObject(url, String.class);
        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity, String.class);
//        String response = restTemplate.getForObject(url, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();

        try {
            // convert JSON string to `JsonNode`
            JsonNode node = mapper.readTree(response.getBody());

            access_token = node.path("access_token").asText();
            refresh_token = node.path("refresh_token").asText();

        } catch (JsonProcessingException ex) {
            Logger.getLogger(BhaashikSpringRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Response: " + response);
        System.out.println("Access token: " + access_token);
        System.out.println("Refresh token: " + refresh_token);

        return refresh_token;
    }

    public BhaashikAnnotationManagementUpdateInfo getAnnotationManagementUpdateInfo() throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_ANNOTATION_MANAGEMENT_INFO;
        String requestBody = "body";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

//        ResponseEntity<BhaashikAnnotationManagementUpdateInfo> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                new ParameterizedTypeReference<BhaashikAnnotationManagementUpdateInfo>() {
//                });
//
//        BhaashikAnnotationManagementUpdateInfo annotationManagementUpdateInfo = response.getBody();

        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                String.class);

        String annotationManagementInfoString = plainObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody());
        System.out.println("Annotation management info received: " + annotationManagementInfoString);

        //
//        TypeReference<Map<String,BhaashikUser>> typeRef
//                = new TypeReference<Map<String,BhaashikUser>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
        BhaashikAnnotationManagementUpdateInfo annotationManagementUpdateInfo = plainObjectMapper.readValue(response.getBody(), BhaashikAnnotationManagementUpdateInfo.class);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
//        ObjectMapper mapper = new ObjectMapper();
        annotationManagementInfoString = plainObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(annotationManagementUpdateInfo);
//        String annotationManagementInfoString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(annotationManagementUpdateInfo);
        System.out.println("Annotation management info after deserialzation and reserialization: " + annotationManagementInfoString);

        return annotationManagementUpdateInfo;
    }

    public BhaashikAnnotationManagementUpdateInfo saveAnnotationManagementUpdateInfo(BhaashikAnnotationManagementUpdateInfo annotationManagementUpdateInfo) throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.SAVE_ANNOTATION_MANAGEMENT_INFO;

//        ObjectMapper mapper = new ObjectMapper();
        String requestBody = plainObjectMapper.writeValueAsString(annotationManagementUpdateInfo);
//        String requestBody = mapper.writeValueAsString(annotationManagementUpdateInfo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<BhaashikAnnotationManagementUpdateInfo> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<BhaashikAnnotationManagementUpdateInfo>() {
                });
//
        annotationManagementUpdateInfo = response.getBody();
//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);

        String annotationManagementInfoString = plainObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody());
        System.out.println("Annotation management info received: " + annotationManagementInfoString);

//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
//        TypeReference<Map<String,BhaashikUser>> typeRef
//                = new TypeReference<Map<String,BhaashikUser>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, BhaashikUser> userMap = mapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
//        String annotationManagementInfoString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(annotationManagementUpdateInfo);
        annotationManagementInfoString = plainObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(annotationManagementUpdateInfo);
//        String annotationManagementInfoString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(annotationManagementUpdateInfo);
        System.out.println("Annotation management info after deserialzation and reserialization: " + annotationManagementInfoString);

        return annotationManagementUpdateInfo;
    }

    public Map<String, BhaashikUserDTO> getAllUsers() throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_USERS;
        String requestBody = "body";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, BhaashikUserDTO>> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<Map<String, BhaashikUserDTO>>() {
        });

        Map<String, BhaashikUserDTO> userDTOMap = response.getBody();

//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
//        TypeReference<Map<String,BhaashikUser>> typeRef
//                = new TypeReference<Map<String,BhaashikUser>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, BhaashikUser> userMap = mapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Users: " + userDTOMap);

        userDTOMap.entrySet().forEach(
                entry -> {
                    System.out.println("User username: " + entry.getKey());
                    System.out.println("User first name: " + entry.getValue().getFirstName());
                }
        );

        return userDTOMap;
    }

    public Map<String, BhaashikOrganisationDTO> getAllOrganisations() throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_ORGANISATIONS;
        String requestBody = "body";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, BhaashikOrganisationDTO>> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<Map<String, BhaashikOrganisationDTO>>() {
        });

        Map<String, BhaashikOrganisationDTO> organisations = response.getBody();

//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
//        TypeReference<Map<String,BhaashikOrganisation>> typeRef
//                = new TypeReference<Map<String,BhaashikOrganisation>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, BhaashikOrganisation> organisations = mapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Organisations: " + organisations);

        organisations.entrySet().forEach(
                entry -> {
                    System.out.println("Organisation short name: " + entry.getKey());
                    System.out.println("Organisation long name: " + entry.getValue().getLongName());
                }
        );

        return organisations;
    }

    public Map<String, BhaashikOrganisationDTO> getUserOrganisations(String username) throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_USER_ORGANISATIONS;
//        String requestBody = "{ \"filePath\": \"" + remoteFile.getRelativePath() + "\"}";
        String requestBody = username;
//        String requestBody = "{ \"username\": \"" + username + "\" }";
//        String requestBody = "username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, BhaashikOrganisationDTO>> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<Map<String, BhaashikOrganisationDTO>>() {
        });

        Map<String, BhaashikOrganisationDTO> organisations = response.getBody();

        //        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
//        TypeReference<Map<String,BhaashikOrganisation>> typeRef
//                = new TypeReference<Map<String,BhaashikOrganisation>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, BhaashikOrganisation> organisations = mapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Organisations: " + organisations);

        organisations.entrySet().forEach(
                entry -> {
                    System.out.println("Organisation short name: " + entry.getKey());
                    System.out.println("Organisation long name: " + entry.getValue().getLongName());
                }
        );

        return organisations;
    }

    public Map<String, BhaashikResourceLanguageDTO> getAllLanguages() throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_LANGUAGES;
        String requestBody = "body";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, BhaashikResourceLanguageDTO>> response = restTemplate.exchange(authenticationURL, HttpMethod.POST
                , entity,
                new ParameterizedTypeReference<Map<String, BhaashikResourceLanguageDTO>>() {
        });

        Map<String, BhaashikResourceLanguageDTO> languages = response.getBody();

//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
//        TypeReference<Map<String,BhaashikResourceLanguage>> typeRef
//                = new TypeReference<Map<String,BhaashikResourceLanguage>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, BhaashikResourceLanguage> languages = mapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Languages: " + languages);

        languages.entrySet().forEach(
                entry -> {
                    System.out.println("Language name: " + entry.getKey());
                }
        );

        return languages;
    }

    public Map<String, BhaashikResourceLanguageDTO> getUserLanguages(String username) throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_USER_LANGUAGES;
        String requestBody = username;
//        String requestBody = "{ \"username\": \"" + username + "\"}";
//        String requestBody = "username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, BhaashikResourceLanguageDTO>> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<Map<String, BhaashikResourceLanguageDTO>>() {
                });

        Map<String, BhaashikResourceLanguageDTO> languages = response.getBody();

//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
//        TypeReference<Map<String,BhaashikResourceLanguage>> typeRef
//                = new TypeReference<Map<String,BhaashikResourceLanguage>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, BhaashikResourceLanguage> languages = mapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Languages: " + languages);

        languages.entrySet().forEach(
                entry -> {
                    System.out.println("Language name: " + entry.getKey());
                }
        );

        return languages;
    }

    public Map<String, BhaashikAnnotationLevelDTO> getAllAnnotationLevels() throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_ANNOTATION_LEVELS;
        String requestBody = "body";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, BhaashikAnnotationLevelDTO>> response = restTemplate.exchange(authenticationURL, HttpMethod.POST
                , entity,
                new ParameterizedTypeReference<Map<String, BhaashikAnnotationLevelDTO>>() {
        });

        Map<String, BhaashikAnnotationLevelDTO> annotationLevelMap = response.getBody();

//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
//        TypeReference<Map<String,BhaashikAnnotationLevel>> typeRef
//                = new TypeReference<Map<String,BhaashikAnnotationLevel>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, BhaashikAnnotationLevel> annotationLevelMap = mapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Annotation levels: " + annotationLevelMap);

        annotationLevelMap.entrySet().forEach(
                entry -> {
                    System.out.println("Annotation level name: " + entry.getKey());
                }
        );

        return annotationLevelMap;
    }

    public Map<String, BhaashikAnnotationLevelDTO> getUserAnnotationLevels(String username) throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_USER_ANNOTATION_LEVELS;
        String requestBody = username;
//        String requestBody = "{ \"username\": \"" + username + "\"}";
//        String requestBody = "username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, BhaashikAnnotationLevelDTO>> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<Map<String, BhaashikAnnotationLevelDTO>>() {
                });

        Map<String, BhaashikAnnotationLevelDTO> annotationLevelMap = response.getBody();

//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
//        TypeReference<Map<String,BhaashikAnnotationLevel>> typeRef
//                = new TypeReference<Map<String,BhaashikAnnotationLevel>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, BhaashikAnnotationLevel> annotationLevelMap = mapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Annotation levels: " + annotationLevelMap);

        annotationLevelMap.entrySet().forEach(
                entry -> {
                    System.out.println("Annotation level name: " + entry.getKey());
                }
        );

        return annotationLevelMap;
    }

    public BhaashikUserDTO getCurrentUser() throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_CURRENT_USER;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<BhaashikUserDTO> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<BhaashikUserDTO>() {
                });
//        ResponseEntity<BhaashikUserDTO> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                BhaashikUserDTO.class);

        BhaashikUserDTO currentUser = response.getBody();

//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
//        BhaashikUser currentUser = response.getBody();
//
//        TypeReference<BhaashikUserDTO> typeRef
//                = new TypeReference<BhaashikUserDTO>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        BhaashikUserDTO currentUser = plainObjectMapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("User: " + currentUser);

        this.user = currentUser;

        return currentUser;
    }

    public BhaashikUserDTO getUser(String username) {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_USER;
        String requestBody = "username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<BhaashikUserDTO> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<BhaashikUserDTO>() {
                });

        BhaashikUserDTO currentUser = response.getBody();

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("User: " + user);

        return currentUser;
    }

    public Boolean doesUserExist(String username) {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.DOES_USER_EXIST;
        String requestBody = "username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Boolean> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<Boolean>() {
                });

        Boolean userExists = response.getBody();

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Does user exist: " + userExists);

        return userExists;
    }

    public Boolean doesEmailExist(String email) {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.DOES_EMAIL_EXIST;
        String requestBody = "email=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Boolean> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<Boolean>() {
                });

        Boolean emailExists = response.getBody();

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Does user exist: " + emailExists);

        return emailExists;
    }

    public Map<String, BhaashikRoleDTO> getUserRoles(String username) throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_USER_ROLES;
        String requestBody = username;
//        String requestBody = "{ \"username\": \"" + username + "\"}";
//        String requestBody = "username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, BhaashikRoleDTO>> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<Map<String, BhaashikRoleDTO>>() {
        });

        Map<String, BhaashikRoleDTO> roleMap = response.getBody();

//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
////        Map<String, BhaashikRole> roles = response.getBody();
//
//        TypeReference<Map<String,BhaashikRole>> typeRef
//                = new TypeReference<Map<String,BhaashikRole>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, BhaashikRole> roleMap = mapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Roles: " + roleMap);

        roleMap.entrySet().forEach(
                entry -> {
                    System.out.println("Role name: " + entry.getKey());
                }
        );

        return roleMap;
    }

    public Map<String, BhaashikRoleDTO> getAllRoles() throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_ROLES;
        String requestBody = "body";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, BhaashikRoleDTO>> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<Map<String, BhaashikRoleDTO>>() {
                });

        Map<String, BhaashikRoleDTO> roleMap = response.getBody();

//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
////        Map<String, BhaashikRole> roles = response.getBody();
//
//        TypeReference<Map<String,BhaashikRole>> typeRef
//                = new TypeReference<Map<String,BhaashikRole>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, BhaashikRole> roleMap = mapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Roles: " + roleMap);

        roleMap.entrySet().forEach(
                entry -> {
                    System.out.println("Role name: " + entry.getKey());
                }
        );

        return roleMap;
    }

    public Map<String, BhaashikRoleDTO> getRoles(String username) throws JsonProcessingException {
        String authenticationURL = AUTH_BASE_URL + BhaashikSpringServerEndPoints.GET_USER_ROLES;
        String requestBody = "body";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, BhaashikRoleDTO>> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<Map<String, BhaashikRoleDTO>>() {
                });

        Map<String, BhaashikRoleDTO> roleMap = response.getBody();

//        ResponseEntity<String> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
//                String.class);
//
////        Map<String, BhaashikRole> roles = response.getBody();
//
//        TypeReference<Map<String,BhaashikRole>> typeRef
//                = new TypeReference<Map<String,BhaashikRole>>() {};
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, BhaashikRole> roleMap = mapper.readValue(response.getBody(), typeRef);

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Roles: " + roleMap);

        roleMap.entrySet().forEach(
                entry -> {
                    System.out.println("Role name: " + entry.getKey());
                }
        );

        return roleMap;
    }

    public boolean hasPermissionToPath(String username, RemoteFile path) {
        return true;
    }

    public String getPermittedDefaultPath(String username) {
        return "";
    }

    public RemoteFileNode getPermittedDefaultPathTree(String username) {
        RemoteFileNode remoteFileNode = null;

        return remoteFileNode;
    }

    public RemoteFileNode getAnnotationDirNode() {
        RemoteFileNode remoteFileNode = null;

        return remoteFileNode;
    }

    public RemoteFile getAnnotationDirectory() throws JsonProcessingException {

        String authenticationURL = FILE_BASE_URL + BhaashikSpringServerEndPoints.GET_BASE_ANNOTATION_DIRECTORY;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);
//        HttpEntity<RemoteFile> entity = new HttpEntity<>(remoteFile, headers);
//        entity.

        ResponseEntity<RemoteFile> response = restTemplate.exchange(authenticationURL, HttpMethod.GET, entity,
                new ParameterizedTypeReference<RemoteFile>() {
                });

        RemoteFile remoteFile = response.getBody();

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Annotation directory: " + remoteFile);

        return remoteFile;
    }

    public List<RemoteFile> listFilesOnServer(String username, RemoteFile remoteFile) throws JsonProcessingException {

        String authenticationURL = FILE_BASE_URL + BhaashikSpringServerEndPoints.LIST_FILES_IN_DIRECTORY;
//        String requestBody = "filePath=" + remoteFile.getRelativePath();
//        String requestBody = "{ \"filePath\": \"" + remoteFile.getRelativePath() + "\"}";
//        ObjectMapper mapper = new ObjectMapper();
        String requestBody = plainObjectMapper.writeValueAsString(remoteFile);
//        String requestBody = mapper.writeValueAsString(remoteFile);

        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(access_token);
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//        HttpEntity<RemoteFile> entity = new HttpEntity<>(remoteFile, headers);
//        entity.

        ResponseEntity<List<RemoteFile>> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<List<RemoteFile>>() {
        });

        List<RemoteFile> remoteFiles = response.getBody();

        String charset = remoteFile.getCharset();

        remoteFiles.forEach(
                rfile -> rfile.setCharset(charset)
        );

        System.out.println("Response: " + response);
        System.out.println("Response body: " + response.getBody());
        System.out.println("Files: " + remoteFiles);

        return remoteFiles;
    }

    public Boolean isDirectory(String username, RemoteFile remoteFile) throws JsonProcessingException {
        String authenticationURL = FILE_BASE_URL + BhaashikSpringServerEndPoints.IS_DIRECTORY;
//        ObjectMapper mapper = new ObjectMapper();
//        String requestBody = mapper.writeValueAsString(remoteFile);
        String requestBody = plainObjectMapper.writeValueAsString(remoteFile);

        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(access_token);
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//        HttpEntity<RemoteFile> entity = new HttpEntity<>(remoteFile, headers);
//        entity.

        ResponseEntity<Boolean> response = restTemplate.exchange(authenticationURL, HttpMethod.POST, entity,
                new ParameterizedTypeReference<Boolean>() {
                });

        Boolean isDirectory = response.getBody();

        System.out.printf("Is %s directory: %s\n", remoteFile.getRelativePath(), isDirectory);

        return isDirectory;
    }

    public boolean listDirectories(String parentDirectory, RemoteFileNode rootNode, String charset) throws JsonProcessingException {
        String foundPath = null;
        String absPathServer = null;
        String absPathClient = null;
        RemoteFile annotationDirRF = getAnnotationDirectory();
        String defaultServerDirPath = annotationDirRF.getAbsolutePathOnServer();

        List<RemoteFile> remoteFiles = null;

        if(parentDirectory.equals(""))
        {
            remoteFiles = listFilesOnServer(user.getUsername(), annotationDirRF);
        }
        else
        {
            annotationDirRF = new RemoteFile(parentDirectory, annotationDirRF.getRelativePath() + "/" + parentDirectory,
                    defaultServerDirPath + "/" + parentDirectory, null, charset, true);
            remoteFiles = listFilesOnServer(user.getUsername(), annotationDirRF);
        }

        RemoteFileNode node = null;
        File remoteFile = null;
//        File rootRemoteFile = null;

//        if(rootNode != null)
//        {
//            rootRemoteFile = rootNode.getRemoteRMIFile();
//        }

        for (RemoteFile rfile : remoteFiles)
        {
            if(rfile == null)
            {
                System.out.println("Null remote RMI file");
                return false;
            }

            remoteFile = new File(rfile.getRelativePath());

            // If file
//            if (!file.isDirectory())
            if (!isDirectory(username, rfile))
            {
                if(!rfile.getFileName().startsWith("."))
                {
                    if(parentDirectory.endsWith("/") || parentDirectory.equals(""))
                    {
                        foundPath = parentDirectory + rfile.getFileName();
                        absPathServer = defaultServerDirPath + "/" + foundPath;
                    }
                    else
                    {
                        foundPath = parentDirectory + "/" + rfile.getFileName();
                        absPathServer = foundPath;
                    }

                    System.out.println("Found path: " + foundPath);

//                    absPathServer = rmiFS.getAbsolutePathOnServer(file);
//                    absPathServer = foundPath;
                    System.out.println("Absolute path on server: " + absPathServer);

//                    foundPath = rmiFS.getRelativePathOnServer(absPathServer, defaultServerDirPath);
                    foundPath = rfile.getRelativePath();

                    absPathClient = BhaashikSpringClientUtils.getAbsolutePathOnClient(user, foundPath);
                    System.out.println("Absolute path on client: " + absPathClient);

                    rfile.setAbsolutePathOnClient(absPathClient);

                    System.out.println("Relative found path on server: " + foundPath);

//                    RemoteFile rfile = new RemoteFile(rfile.getName(), foundPath, absPathServer, absPathClient, false);

//                    remoteFile = new RemoteSftpFile(entry, rootRemoteFile, foundPath);
//                    node = RemoteFileNode.getRemoteFileNodeInstance(null, remoteFile, rfile, rmiFS, RemoteFileNode.RMI_MODE);
//                    node = RemoteFileNode.getRemoteFileNodeInstance(null, null, rfile, rmiFS, RemoteFileNode.RMI_MODE);
                    node = RemoteFileNode.getRemoteFileNodeInstance(null, null, rfile, RemoteFileNode.SPRING_MODE);
                    rootNode.add(node);

                    //                list.add(foundPath);

                    System.out.println("Found file:" + foundPath);
                }
            }
            // If directory
            else
            {
                if (!rfile.getFileName().equals(".") &&
                        !rfile.getFileName().equals("..") &&
                        !rfile.getFileName().startsWith("."))
                {
                    if(parentDirectory.endsWith("/") || parentDirectory.equals(""))
                    {
                        foundPath = parentDirectory + rfile.getFileName();
                        absPathServer = defaultServerDirPath + "/" + foundPath;
                    }
                    else
                    {
                        foundPath = parentDirectory + "/" + rfile.getFileName();
                        absPathServer = foundPath;
                    }

                    System.out.println("Found path: " + foundPath);

//                    absPathServer = rmiFS.getAbsolutePathOnServer(file);
                    System.out.println("Absolute path on server: " + absPathServer);

//                    foundPath = rmiFS.getRelativePathOnServer(absPathServer, defaultServerDirPath);
                    foundPath = rfile.getRelativePath();

                    absPathClient = BhaashikSpringClientUtils.getAbsolutePathOnClient(user, foundPath);
                    System.out.println("Absolute path on client: " + absPathClient);

//                    rfile.setIsDirectory(true);
                    rfile.setAbsolutePathOnClient(absPathClient);

                    System.out.println("Relative found path on server: " + foundPath);

//                    RemoteFile rfile = new RemoteFile(rfile.getName(), foundPath, absPathServer, absPathClient, true);

//                    node = RemoteFileNode.getRemoteFileNodeInstance(null, null, rfile, rmiFS, RemoteFileNode.RMI_MODE);
                    node = RemoteFileNode.getRemoteFileNodeInstance(null, null, rfile, RemoteFileNode.SPRING_MODE);
                    rootNode.add(node);

                    listDirectories(foundPath, node, charset);

                    System.out.println("Found file:" + foundPath);
//                    }
                }
            }
        }

        return true;
    }
    public void cloneDirectory(RemoteFileNode remoteFileNode, boolean overwriteExisting) {

        RemoteFile rfile = remoteFileNode.getRemoteRMIFile();

        if(rfile != null)
        {
//            if(rfile.isDirectory())
            if(rfile.isDirectory())
            {
                File dir = new File(rfile.getRelativePath());

                if(!dir.exists())
                    dir.mkdir();

                int ccount = remoteFileNode.getChildCount();

                for (int i = 0; i < ccount; i++) {
                    RemoteFileNode childNode = (RemoteFileNode) remoteFileNode.getChildAt(i);

                    RemoteFile childRemoteFile = childNode.getRemoteRMIFile();

                    if(childRemoteFile != null)
                    {
                        if(childRemoteFile.isDirectory())
                            cloneDirectory(childNode, overwriteExisting);
                        else
                            cloneFile(childRemoteFile, overwriteExisting);
                    }
                }
            }
            else
            {
                cloneFile(rfile, overwriteExisting);
            }
        }
    }
    public boolean cloneFile(RemoteFile remoteFile, boolean overwriteExisting)
    {
        if(remoteFile == null)
            return false;

//        String sourcePath = remoteFile.getRelativePath();
//        String destPath = remoteFile.getRelativePath();
        String sourcePath = remoteFile.getAbsolutePathOnServer();
        String destPath = remoteFile.getAbsolutePathOnClient();

//        destPath = removePrefix(destPath, "./");
//        destPath = homeDir + "/" + destPath;

        try {
            File localFile = new File(destPath);

            if(!localFile.exists() || overwriteExisting)
            {
                System.out.println("Downloading: " + sourcePath + " to " + destPath);
                try {
                    downloadFile(user.getUsername(), remoteFile);
//                    downloadFile(destPath, sourcePath, rmiFileSystem);
                } catch (IOException ex) {
                    Logger.getLogger(RMIUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(JSchSSHUtils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();

            return false;
        }

        return true;
    }
    public boolean uploadFile(String username, RemoteFile remoteFile, boolean overwrite) throws Exception {
//    public boolean downlaodFile(String username, RemoteFile remoteFile) {
        String fileURL = FILE_BASE_URL + BhaashikSpringServerEndPoints.UPLOAD_FILE;
//        String fileURL = "http://localhost:8080/fileServer/upload";

        String textContents = BhaashikSpringClientUtils.readFileAsString(remoteFile.getAbsolutePathOnClient());

        String encodedString = Base64.getEncoder().encodeToString(textContents.getBytes());

        TextFileUpload textFileUpload = new TextFileUpload();

        textFileUpload.setRemoteFile(remoteFile);
        textFileUpload.setTextFileContents(encodedString);

//        ObjectMapper mapper = new ObjectMapper();
        String requestBody = plainObjectMapper.writeValueAsString(textFileUpload);
//        String requestBody = mapper.writeValueAsString(textFileUpload);

        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(access_token);
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        // This nested HttpEntiy is important to create the correct
        // Content-Disposition entry with metadata "name" and "filename"
//        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
//        ContentDisposition contentDisposition = ContentDisposition
//                .builder("form-data")
//                .name("file")
//                .filename(remoteFile.getFileName())
//                .build();

//        String uploadFilePath = remoteFile.getRelativePath();
//        FileSystemResource fileSystemResource = BhaashikSpringClientUtils.getFileResource(new File(uploadFilePath));

//        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
//        HttpEntity<FileSystemResource> fileEntity = new HttpEntity<>(fileSystemResource, fileMap);

//        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
////        requestBody.add("fileResource", fileEntity);
//        requestBody.add("fileResource", fileSystemResource);
//        requestBody.add("remoteFile", textFileUploadJSon);

//        HttpEntity<MultiValueMap<String, Object>> requestEntity =
//                new HttpEntity<>(requestBody, headers);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//        HttpEntity<RemoteFile> entity = new HttpEntity<>(remoteFile, headers);

        try {
            ResponseEntity<UploadFileResponse> response = restTemplate.exchange(
                    fileURL,
                    HttpMethod.POST, entity, new ParameterizedTypeReference<UploadFileResponse>(){});

            System.out.println("Response: " + response);

        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean uploadMultipleFiles(String username, List<RemoteFile> filePaths, boolean overwrite) {
        return true;
    }

    public boolean downloadFile(String username, RemoteFile remoteFile) throws JsonProcessingException {
//    public boolean downlaodFile(String username, RemoteFile remoteFile) {
        String fileURL = FILE_BASE_URL + BhaashikSpringServerEndPoints.DOWNLOAD_FILE;

//        ObjectMapper mapper = new ObjectMapper();
        String requestBody = plainObjectMapper.writeValueAsString(remoteFile);
//        String requestBody = mapper.writeValueAsString(remoteFile);

        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(access_token);
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
        headers.add(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN_VALUE);

        String authHeader = "Bearer " + access_token;

        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//        HttpEntity<RemoteFile> entity = new HttpEntity<>(remoteFile, headers);

        try {
            ResponseEntity<Resource> response = restTemplate.exchange(
                    fileURL,
                    HttpMethod.POST, entity, Resource.class);
//            ResponseEntity<EncodedResource> response = restTemplate.exchange(
//                    fileURL,
//                    HttpMethod.POST, entity, EncodedResource.class);

            System.out.println("Response: " + response);

            if (response.getStatusCode() == HttpStatus.OK) {
                String disposition = response.getHeaders().get("Content-Disposition").get(0);
                String fileName = disposition.replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1");//get the filename from the Content-Disposition header
                fileName = URLDecoder.decode(fileName, String.valueOf(StandardCharsets.ISO_8859_1));

                BhaashikUserDTO currentUser = getCurrentUser();
                String annotationDir = BhaashikSpringClientUtils.buildClientAnnotationFolderPath(currentUser);
                System.out.printf("Annotation directory: %s\n", annotationDir);
                //save to examine file
                System.out.printf("Download file relative path: %s\n", remoteFile.getRelativePath());
                File targetFile = new File(annotationDir, remoteFile.getRelativePath());
                targetFile.getParentFile().mkdirs();

                System.out.println("Saving downloaded file to: " + targetFile.getAbsolutePath());

                FileUtils.copyInputStreamToFile(response.getBody().getInputStream(), targetFile);
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

//    public boolean downlaodFile(String username, String filePath) {
////    public boolean downlaodFile(String username, RemoteFile remoteFile) {
//        String fileURL = "http://localhost:8080/fileServer/downloadFile";
//
//        String requestBody = "filePath=" + filePath;
//        HttpHeaders headers = new HttpHeaders();
////        headers.setBearerAuth(access_token);
////        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
////        headers.setContentType(MediaType.APPLICATION_JSON);
////        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
//        headers.add(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN_VALUE);
//
//        String authHeader = "Bearer " + access_token;
//
//        headers.set("Authorization", authHeader);
//
//        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
////        HttpEntity<RemoteFile> entity = new HttpEntity<>(remoteFile, headers);
//
//        try {
//            ResponseEntity<Resource> response = restTemplate.exchange(
//                    fileURL,
//                    HttpMethod.POST, entity, Resource.class);
//
//            System.out.println("Response: " + response);
//
//            if (response.getStatusCode() == HttpStatus.OK) {
//                String disposition = response.getHeaders().get("Content-Disposition").get(0);
//                String fileName = disposition.replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1");//get the filename from the Content-Disposition header
//                fileName = URLDecoder.decode(fileName, String.valueOf(StandardCharsets.ISO_8859_1));
//
//                //save to examine file
//                File targetFile = new File(".", fileName);
//
//                System.out.println("Saving downloaded file to: " + targetFile.getAbsolutePath());
//
//                FileUtils.copyInputStreamToFile(response.getBody().getInputStream(), targetFile);
//            }
//        } catch (RestClientException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
    
    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 500000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setReadTimeout(timeout);
        return clientHttpRequestFactory;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        
        BhaashikSpringRestClient bhaashikSpringRestClient = BhaashikSpringClientUtils.getBhaashikSpringRestClientInstance();

//        BhaashikSpringRestClient bhaashikSpringRestClient = new BhaashikSpringRestClient();

        String server = "http://localhost:8080/bhaashik-server";

        bhaashikSpringRestClient.authenticateUser(server, "socrates", "1234");
        bhaashikSpringRestClient.getCurrentUser();

//        bhaashikSpringRestClient.getUserRoles("Socrates");
//        bhaashikSpringRestClient.getUserOrganisations("Socrates");
//        bhaashikSpringRestClient.getUserLanguages("Socrates");
//        bhaashikSpringRestClient.getUserAnnotationLevels("Socrates");

//        long start = System.currentTimeMillis();
//        Thread.sleep(1 * 60 * 1000);
//
//        System.out.print("Sleep time in ms = \r"+(System.currentTimeMillis()-start));
//        System.out.print();
        
        
//        bhaashikSpringRestClient.refreshToken();
//        List<BhaashikUser> users = bhaashikSpringRestClient.getAllUsers();

//        int i = 0;
//        while(true) {
//            Thread.sleep(200);
//            bhaashikSpringRestClient.getUser("democritus");
//        }
//        bhaashikSpringRestClient.getRoles("socrates");
//        Map<String, BhaashikOrganisationDTO> bhaashikOrganisationMap = bhaashikSpringRestClient.getAllOrganisations();
//        BhaashikAnnotationManagementUpdateInfo annotationManagementUpdateInfo = bhaashikSpringRestClient.getAnnotationManagementUpdateInfo();
//        bhaashikSpringRestClient.getAnnotationDirectory();
//        RemoteFile remoteFile = new RemoteFile("name", ".", ".", ".", true);
//        bhaashikSpringRestClient.listFilesOnServer("plato", remoteFile);
//        String filePath = "Premchand/kahAnI-MS1-20.utf8";
//        remoteFile = new RemoteFile("name", filePath, ".", ".", true);
//        bhaashikSpringRestClient.downloadFile("plato", remoteFile);
//        filePath = "Premchand";
//        remoteFile = new RemoteFile("name", filePath, ".", ".", true);
//        bhaashikSpringRestClient.isDirectory("plato", remoteFile);
//        filePath = "Premchand/kahAnI-MS1-20.utf8";
//        remoteFile = new RemoteFile("name", filePath, ".", ".", true);
//        bhaashikSpringRestClient.isDirectory("plato", remoteFile);
//        bhaashikSpringRestClient.downlaodFile("aristotle", "./temp.txt");
//        String filePath = "temp.txt";
//
//        RemoteFile remoteFile = new RemoteFile("temp.txt", filePath, filePath, filePath, true);
//        try {
//            bhaashikSpringRestClient.uploadFile("plato", remoteFile, true);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

//        RemoteFileNode rootRemoteFileNode = RemoteFileNode.getRemoteFileNodeInstance(null, null, null, RemoteFileNode.SPRING_MODE);
//
//        try {
//            File homeDir = FileUtils.getUserDirectory();
//            File workingDir = new File(homeDir, "annotation");
//
//            if (!workingDir.exists()) {
//                try {
//                    workingDir.mkdir();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            bhaashikSpringRestClient.listDirectories("", rootRemoteFileNode);
//
//            System.out.println(rootRemoteFileNode);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
    }
}
