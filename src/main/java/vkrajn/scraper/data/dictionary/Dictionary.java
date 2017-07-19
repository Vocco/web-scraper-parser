package vkrajn.scraper.data.dictionary;

import java.util.List;
import vkrajn.scraper.data.WordFrequency;

/**
 * A dictionary interface for handling extracted words form a text.
 *
 * @author Vojtech Krajnansky
 * @version 07/18/2017
 */
public interface Dictionary {

    /**
     * Insert a word into the dictionary.
     *
     * @param string The word to insert.
     */
    void insert(String string);

    /**
     * Get frequency for a given word.
     *
     * @param string word to find frequency for
     * @return frequency of the word in the dictionary if it exists, -1
     * otherwise
     */
    int getWordFrequency(String string);

    /**
     * Gets the most frequent letter in the dictionary, with respect to word
     * counts.
     *
     * @return the most frequent letter in the dictionary, null if the
     * dictionary is empty
     */
    Character getMostFrequentLetter();

    /**
     * Gets frequencies for all words in the dictionary.
     *
     * @return a {@link List} of {@link WordFrequency} objects for each word in
     * the dictionary
     */
    List<WordFrequency> getWordFrequencies();

    /**
     * Finds the longest words from the dictionary.
     *
     * @return a {@link List} of longest words in the dictionary
     */
    List<String> getLongestWords();
}
