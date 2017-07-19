package vkrajn.scraper.data.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import vkrajn.scraper.data.WordFrequency;
import vkrajn.scraper.utils.MutableInt;

/**
 * A {@link Dictionary} implemented using a {@link TreeMap}.
 *
 * @author Vojtech Krajnansky
 * @version 07/19/2017
 */
public class DictionaryTreeMapImpl implements Dictionary {

    // Attributes
    private final Map<String, Integer> words;
    private final Map<Character, MutableInt> letterFrequency;
    private final List<Entry<Character, MutableInt>> sortedLetters;

    // Constructors
    public DictionaryTreeMapImpl() {
        words = new TreeMap<>((o1, o2) -> {
            int compare = Integer.compare(o2.length(), o1.length());
            if (compare == 0) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
            
            return compare;
        });
        letterFrequency = new HashMap<>();
        sortedLetters = new ArrayList<>();
    }

    // Override Methods
    @Override
    public void insert(String string) {
        if (string == null || string.length() == 0) {
            return;
        }

        if (!words.containsKey(string)) {
            words.put(string, 1);
        } else {
            int oldFrequency = words.get(string);
            words.put(string, oldFrequency + 1);
        }

        increaseLetterFreq(string);
    }

    @Override
    public int getWordFrequency(String string) {
        if (string == null || string.isEmpty()) {
            return -1;
        }

        if (words.containsKey(string)) {
            return words.get(string);
        }

        return -1;
    }

    @Override
    public Character getMostFrequentLetter() {

        sortedLetters.clear();
        sortedLetters.addAll(letterFrequency.entrySet());

        // Sort the characters decreasing with respect to their frequency
        Collections.sort(sortedLetters, (o1, o2) -> {
            return o2.getValue().get() - o1.getValue().get();
        });

        if (sortedLetters.isEmpty()) {
            return null;
        }

        return sortedLetters.get(0).getKey();
    }

    @Override
    public List<WordFrequency> getWordFrequencies() {
        List<WordFrequency> wordFrequencies = new ArrayList<>();

        words.forEach((word, frequency) -> {
            wordFrequencies.add(new WordFrequency(word, frequency));
        });

        return wordFrequencies;
    }

    @Override
    public List<String> getLongestWords() {
        List<String> longestWords = new ArrayList<>();
        Iterator i = words.keySet().iterator();
        
        /*
            Get length of first element in the TreeMap, since this is sorted
            by key length, this is the length of the longest words
        */
        if (i.hasNext()) {
            String longestWord = i.next().toString();
            
            int length = longestWord.length();
            longestWords.add(longestWord);
            
            while (i.hasNext()) {
                String candidate = i.next().toString();
                if (candidate.length() == length) {
                    longestWords.add(candidate);
                } else {
                    break;
                }
            }
        }
        
        return longestWords;
    }

    // Helper Methods
    /**
     * Increases the frequency of characters in the string, inserts into the
     * {@link Map} if they don't exist yet.
     *
     * @param string string of characters to increase frequency for
     */
    private void increaseLetterFreq(String string) {
        for (int i = 0; i < string.length(); i++) {
            MutableInt count = letterFrequency.get(string.charAt(i));

            if (count == null) {
                letterFrequency.put(string.charAt(i), new MutableInt());
            } else {
                count.increment();
            }
        }
    }

}
