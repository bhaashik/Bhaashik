java -Xmx800m  -Djava.ext.dirs=lib -cp .:dist/Bhaashik.jar:build/classes -Djava.security.policy=policy bhaashik.text.enc.conv.EncodingConverter $@