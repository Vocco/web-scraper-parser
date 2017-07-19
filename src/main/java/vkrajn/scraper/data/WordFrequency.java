package vkrajn.scraper.data;

/**
 * An encapsulating class to pair words with their frequencies in the
 * dictionary.
 *
 * @author Vojtech Krajnansky
 * @version 07/18/2017
 */
public class WordFrequency {

    // Attributes
    private String word;
    private int frequency;

    // Constructors
    public WordFrequency(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    // Getters
    public String getWord() {
        return word;
    }

    public int getFrequency() {
        return frequency;
    }

    // Setters
    public void setFrequency(int freq) {
        frequency = freq;
    }

    public void setWord(String str) {
        word = str;
    }
}
