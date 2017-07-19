package vkrajn.scraper.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract parser for a web page.
 *
 * @author Vojtech Krajnansky
 * @version 07/18/2017
 */
public abstract class Parser {

    // Constants
    public static final int TIMEOUT = 10000;

    // Abstract Methods
    /**
     * Parses a given web page to plaintext.
     *
     * @param url a {@link String} representation of the page URL
     * @return text content of the page
     * @throws java.io.IOException
     */
    public abstract String getText(String url) throws IOException;

    // Public Methods
    /**
     * Parses given text input into words.
     *
     * @param text text to be parsed
     * @return a {@link List} of words
     */
    public List<String> getWords(String text) {
        List<String> words = new ArrayList<>();

        /*
            TODO: Implement a 3rd-party Parser/Lemmatizer (e.g. 
            https://nlp.stanford.edu/software/lex-parser.shtml for parsing?)
        */
        
        // Match against the pattern
        Pattern p = Pattern.compile("[\\w']+", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher m = p.matcher(text);

        while (m.find()) {
            words.add(text.substring(m.start(), m.end()));
        }

        return words;
    }
}
