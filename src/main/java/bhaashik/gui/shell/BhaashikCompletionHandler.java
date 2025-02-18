/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.gui.shell;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import jline.CandidateListCompletionHandler;
import jline.CompletionHandler;
import jline.CursorBuffer;

/**
 *
 * @author anil
 */
public class BhaashikCompletionHandler extends CandidateListCompletionHandler implements CompletionHandler {

    private static ResourceBundle loc = ResourceBundle.
        getBundle(CandidateListCompletionHandler.class.getName());

    private boolean eagerNewlines = true;

    public boolean complete(final BhaashikConsoleReader reader, final List candidates,
                            final int pos) throws IOException {
        CursorBuffer buf = reader.getCursorBuffer();

        // if there is only one completion, then fill in the buffer
        if (candidates.size() == 1) {
            String value = candidates.get(0).toString();

            // fail if the only candidate is the same as the current buffer
            if (value.equals(buf.toString())) {
                return false;
            }

            setBuffer(reader, value, pos);

            return true;
        } else if (candidates.size() > 1) {
            String value = getUnambiguousCompletions(candidates);
            String bufString = buf.toString();
            setBuffer(reader, value, pos);
        }

        if (eagerNewlines)
            reader.printNewline();
        printCandidates(reader, candidates, eagerNewlines);

        // redraw the current console buffer
        reader.drawLine();

        return true;
    }

    public static void setBuffer(BhaashikConsoleReader reader, String value, int offset)
                           throws IOException {
        while ((reader.getCursorBuffer().cursor > offset)
                   && reader.backspace()) {
            ;
        }

        reader.putString(value);
        reader.setCursorPosition(offset + value.length());
    }

    /**
     *  Print out the candidates. If the size of the candidates
     *  is greated than the {link getAutoprintThreshhold},
     *  they prompt with aq warning.
     *
     *  @param  candidates  the list of candidates to print
     */
    public static final void printCandidates(BhaashikConsoleReader reader,
                                       Collection candidates, boolean eagerNewlines)
                                throws IOException {
        Set distinct = new HashSet(candidates);

        if (distinct.size() > reader.getAutoprintThreshhold()) {
            if (!eagerNewlines)
                reader.printNewline();
            reader.printString(MessageFormat.format
                (loc.getString("display-candidates"), new Object[] {
                    Integer.valueOf(candidates .size())
                    }) + " ");

            reader.flushConsole();

            int c;

            String noOpt = loc.getString("display-candidates-no");
            String yesOpt = loc.getString("display-candidates-yes");

            while ((c = reader.readCharacter(new char[] {
                yesOpt.charAt(0), noOpt.charAt(0) })) != -1) {
                if (noOpt.startsWith
                    (new String(new char[] { (char) c }))) {
                    reader.printNewline();
                    return;
                } else if (yesOpt.startsWith
                    (new String(new char[] { (char) c }))) {
                    break;
                } else {
                    reader.beep();
                }
            }
        }

        // copy the values and make them distinct, without otherwise
        // affecting the ordering. Only do it if the sizes differ.
        if (distinct.size() != candidates.size()) {
            Collection copy = new ArrayList();

            for (Iterator i = candidates.iterator(); i.hasNext();) {
                Object next = i.next();

                if (!(copy.contains(next))) {
                    copy.add(next);
                }
            }

            candidates = copy;
        }

        reader.printNewline();
        reader.printColumns(candidates);
    }

    /**
     *  Returns a root that matches all the {@link String} elements
     *  of the specified {@link List}, or null if there are
     *  no commalities. For example, if the list contains
     *  <i>foobar</i>, <i>foobaz</i>, <i>foobuz</i>, the
     *  method will return <i>foob</i>.
     */
    private final String getUnambiguousCompletions(final List candidates) {
        if ((candidates == null) || (candidates.size() == 0)) {
            return null;
        }

        // convert to an array for speed
        String[] strings =
            (String[]) candidates.toArray(new String[candidates.size()]);

        String first = strings[0];
        StringBuffer candidate = new StringBuffer();

        for (int i = 0; i < first.length(); i++) {
            if (startsWith(first.substring(0, i + 1), strings)) {
                candidate.append(first.charAt(i));
            } else {
                break;
            }
        }

        return candidate.toString();
    }

    /**
     *  @return  true is all the elements of <i>candidates</i>
     *                          start with <i>starts</i>
     */
    private final boolean startsWith(final String starts,
                                     final String[] candidates) {
        for (int i = 0; i < candidates.length; i++) {
            if (!candidates[i].startsWith(starts)) {
                return false;
            }
        }

        return true;
    }
}
