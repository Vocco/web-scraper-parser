package vkrajn.scraper.data.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import vkrajn.scraper.data.WordFrequency;

/**
 * A {@link Dictionary} implemented using a Trie.
 *
 * @author Vojtech Krajnansky
 * @version 07/19/2017
 */
public class DictionaryTrieImpl implements Dictionary {

    // Attributes
    private final Map<Character, Node> roots;
    private final Map<Character, MutableInt> letterFrequency;
    private final List<Entry<Character, MutableInt>> sortedLetters;
    private final List<WordFrequency> wordFrequencies;
    private final List<String> longestWords;
    private boolean wasUpdated;

    // Constructors
    public DictionaryTrieImpl() {
        roots = new HashMap<>();
        letterFrequency = new HashMap<>();
        sortedLetters = new ArrayList<>();
        wordFrequencies = new ArrayList<>();
        longestWords = new ArrayList<>();
        wasUpdated = true;
    }

    // Override Methods
    @Override
    public int getWordFrequency(String string) {

        if (string == null || string.isEmpty()) {
            return -1;
        }

        if (roots.containsKey(string.charAt(0))) {

            if (string.length() == 1
                    && roots.get(string.charAt(0)).getWordCount() > 0) {
                return roots.get(string.charAt(0)).getWordCount();
            }

            // Recursively traverse the tree to search for the word
            return getFrequencyRec(string.substring(1),
                    roots.get(string.charAt(0)));
        }

        return -1;
    }

    @Override
    public void insert(String string) {
        if (string == null || string.length() == 0) {
            return;
        }

        wasUpdated = true;

        if (!roots.containsKey(string.charAt(0))) {
            roots.put(string.charAt(0), new Node());
        }

        increaseLetterFreq(string.charAt(0));

        /*
            Recursively traverse the tree to add all the characters
            or increment the number of words if it exists
         */
        insertWord(string.substring(1), roots.get(string.charAt(0)));
    }

    @Override
    public Character getMostFrequentLetter() {
        if (wasUpdated) {
            sortedLetters.clear();
            sortedLetters.addAll(letterFrequency.entrySet());

            // Sort the characters decreasing with respect to their frequency
            Collections.sort(sortedLetters,
                    (Entry<Character, MutableInt> o1,
                            Entry<Character, MutableInt> o2) -> {
                        return o2.getValue().get() - o1.getValue().get();
                    });

            wasUpdated = false;
        }

        if (sortedLetters.isEmpty()) {
            return null;
        }
        
        return sortedLetters.get(0).getKey();
    }

    @Override
    public List<WordFrequency> getWordFrequencies() {
        if (!wasUpdated) {
            return wordFrequencies;
        }

        wordFrequencies.clear();

        roots.forEach((prefix, node) -> {
            if (node.getWordCount() > 0) {
                wordFrequencies.add(
                        new WordFrequency(prefix.toString(),
                                node.getWordCount()));
            }

            /*
                Recursively traverse the tree to find the frequencies of all
                words in the dictionary
             */
            wordFrequencies.addAll(
                    getWordFreqsRec(prefix.toString(), node));
        });

        return wordFrequencies;
    }

    @Override
    public List<String> getLongestWords() {
        if (!wasUpdated) {
            return longestWords;
        }

        longestWords.clear();

        MutableInt wordLength = new MutableInt();
        wordLength.set(0);

        roots.forEach((prefix, node) -> {
            int depth = 1;
            if (node.getWordCount() > 0) {
                if (wordLength.get() == 0) {
                    wordLength.increment();
                }

                if (wordLength.get() == 1) {
                    longestWords.add(prefix.toString());
                }
            }

            // Recursively traverse the tree to find the longest words
            getLongestWordsRec(prefix.toString(),
                    wordLength, node, longestWords, depth);
        });

        return longestWords;
    }

    // Helper Methods
    /**
     * Recursive method to insert a word into the dictionary or increase the
     * frequency if it already exists.
     *
     * @param string a remaining suffix of the word to insert
     * @param node node to insert the first character of the suffix into
     */
    private void insertWord(String string, Node node) {
        if (string.length() == 0) {
            return;
        }

        final Node nextChild;

        if (node.getChildren().containsKey(string.charAt(0))) {
            nextChild = node.getChildren().get(string.charAt(0));
        } else {
            nextChild = new Node();
            node.getChildren().put(string.charAt(0), nextChild);
        }

        increaseLetterFreq(string.charAt(0));

        if (string.length() == 1) {
            nextChild.incrementWordCount();
        } else {
            insertWord(string.substring(1), nextChild);
        }
    }

    /**
     * Recursive method to search for a frequency for a given word
     *
     * @param string a remaining suffix of the word to search for
     * @param node node to search for the first character of the suffix in
     * @return frequency of the word if it exists, -1 otherwise
     */
    private int getFrequencyRec(String string, Node node) {
        if (string.length() == 0) {
            if (node.getWordCount() > 0) {
                return node.getWordCount();
            } else {
                return -1;
            }
        }

        if (node.getChildren().containsKey(string.charAt(0))) {
            return getFrequencyRec(string.substring(1),
                    node.getChildren().get(string.charAt(0)));
        } else {
            return -1;
        }
    }

    /**
     * Increases the frequency of a given character, inserts into the
     * {@link Map} if it doesn't exist yet.
     *
     * @param letter character for which to increase the frequency
     */
    private void increaseLetterFreq(char letter) {
        MutableInt count = letterFrequency.get(letter);

        if (count == null) {
            letterFrequency.put(letter, new MutableInt());
        } else {
            count.increment();
        }
    }

    /**
     * Recursive method to find the frequencies of all words in the current
     * subtree of the Trie.
     *
     * @param str a current prefix of the word
     * @param curNode root of the subtree
     * @return a list of frequencies of all words which end in the current
     * subtree
     */
    private List<WordFrequency> getWordFreqsRec(String str, Node curNode) {
        List<WordFrequency> wfs = new ArrayList<>();

        curNode.getChildren().forEach((prefix, node) -> {
            String curPrefix = str.concat(prefix.toString());
            if (node.getWordCount() > 0) {
                wfs.add(
                        new WordFrequency(curPrefix, node.getWordCount()));
            }

            wfs.addAll(getWordFreqsRec(curPrefix, node));
        });

        return wfs;
    }

    /**
     * Recursive method to search for the longest words in the Trie.
     * 
     * @param str current prefix
     * @param curMax current maximum length of found word
     * @param curNode node to recursively search in
     * @param longestWords a list of current longest words
     * @param depth depth of current node's parent
     */
    private void getLongestWordsRec(String str, MutableInt curMax, Node curNode,
            List<String> longestWords, int depth) {

        int curDepth = depth + 1;

        curNode.getChildren().forEach((prefix, node) -> {

            if (node.getWordCount() > 0) {
                if (depth > curMax.get()) {
                    curMax.set(depth);

                    longestWords.clear();
                    longestWords.add(str.concat(prefix.toString()));
                } else if (depth == curMax.get()) {
                    longestWords.add(str.concat(prefix.toString()));
                }
            }

            getLongestWordsRec(str.concat(prefix.toString()),
                    curMax, node, longestWords, curDepth);
        });
    }

    // Inner Classes
    /**
     * A node of the Trie tree.
     */
    private class Node {
        
        // Attributes
        private int wordCount;   // if 0, the current path is not a word
        private Map<Character, Node> children;

        // Constructors
        private Node() {
            this.wordCount = 0;
            this.children = new HashMap<>();
        }
        
        // Getters
        public int getWordCount() {
            return wordCount;
        }
        
        public Map<Character, Node> getChildren() {
            return children;
        }
        
        // Setters
        public void setWordCount(int count) {
            wordCount = count;
        }
        
        // Public Methods
        public void incrementWordCount() {
            wordCount++;
        }
    }

    /**
     * An integer class which is initialized to 1, can never contain null.
     */
    private class MutableInt {
        
        // Attributes
        private int value;

        // Constructors
        private MutableInt() {
            this.value = 1;
        }

        // Getters
        public int get() {
            return value;
        }
        
        // Setters
        public void set(int value) {
            this.value = value;
        }
        
        // Public Methods
        public void increment() {
            ++value;
        }
    }
}
