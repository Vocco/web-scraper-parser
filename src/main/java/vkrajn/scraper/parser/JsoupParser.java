package vkrajn.scraper.parser;

import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * A {@link Parser} using {@link Jsoup} to get text contained on a page.
 *
 * @author Vojtech Krajnansky
 * @version 07/18/2017
 */
public class JsoupParser extends Parser {

    // Override Methods
    @Override
    public String getText(String input) throws IOException {

        URL url = new URL(input);
        Document doc = Jsoup.parse(url, TIMEOUT);
        String text = doc.text();

        return text;
    }
}
