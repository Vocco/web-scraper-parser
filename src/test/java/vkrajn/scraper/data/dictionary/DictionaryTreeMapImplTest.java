package vkrajn.scraper.data.dictionary;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test class for {@link DictionaryTreeMapImpl}.
 * 
 * @author Vojtech Krajnansky
 * @version 07/19/2017
 */
public class DictionaryTreeMapImplTest {

    String[] words = {null,
        "",
        "aWord",
        "longerWord",
        "longestWord",
        "sugar-free",
        "ssssssssss"};

    DictionaryTreeMapImpl dict = new DictionaryTreeMapImpl();

    /**
     * Test of insert method, of class DictionaryTreeMapImpl.
     */
    @Test
    public void testInsert() {
        dict.insert(words[0]);
        dict.insert(words[1]);

        assertTrue(dict.getLongestWords().isEmpty());
        assertTrue(dict.getWordFrequencies().isEmpty());

        dict.insert(words[2]);

        assertTrue(dict.getWordFrequencies().get(0).getWord().equals(words[2]));

        dict.insert(words[2]);
        assertTrue(dict.getWordFrequencies().get(0).getWord().equals(words[2]));
    }

    /**
     * Test of getWordFrequency method, of class DictionaryTreeMapImpl.
     */
    @Test
    public void testGetWordFrequency() {
        dict.insert(words[0]);
        dict.insert(words[1]);
        dict.insert(words[2]);
        assertTrue(dict.getWordFrequency(words[2]) == 1);

        dict.insert(words[2]);
        assertTrue(dict.getWordFrequency(words[2]) == 2);

        dict.insert(words[3]);
        assertTrue(dict.getWordFrequency(words[0]) == -1);
        assertTrue(dict.getWordFrequency(words[1]) == -1);
        assertTrue(dict.getWordFrequency(words[2]) == 2);
        assertTrue(dict.getWordFrequency(words[3]) == 1);
    }

    /**
     * Test of getMostFrequentLetter method, of class DictionaryTrieImpl.
     */
    @Test
    public void testGetMostFrequentLetter() {
        dict.insert(words[0]);
        dict.insert(words[1]);
        assertTrue(dict.getMostFrequentLetter() == null);

        dict.insert(words[2]);
        assertTrue(dict.getMostFrequentLetter().equals('a'));

        dict.insert(words[3]);
        assertTrue(dict.getMostFrequentLetter().equals('r'));

        dict.insert(words[6]);
        assertTrue(dict.getMostFrequentLetter().equals('s'));
    }

    /**
     * Test of getWordFrequencies method, of class DictionaryTrieImpl.
     */
    @Test
    public void testGetWordFrequencies() {
        dict.insert(words[0]);
        dict.insert(words[1]);
        assertTrue(dict.getWordFrequencies().isEmpty());

        dict.insert(words[2]);
        assertTrue(dict.getWordFrequencies().size() == 1);
        assertTrue(dict.getWordFrequencies().get(0).getWord().equals(words[2]));
        assertTrue(dict.getWordFrequencies().get(0).getFrequency() == 1);

        dict.insert(words[3]);
        dict.insert(words[2]);
        assertTrue(dict.getWordFrequencies().size() == 2);
        assertTrue(dict.getWordFrequencies().get(0).getWord().equals(words[3]));
        assertTrue(dict.getWordFrequencies().get(1).getWord().equals(words[2]));
        assertTrue(dict.getWordFrequencies().get(0).getFrequency() == 1);
        assertTrue(dict.getWordFrequencies().get(1).getFrequency() == 2);

        dict.insert(words[4]);
        assertTrue(dict.getWordFrequencies().size() == 3);
    }

    /**
     * Test of getLongestWords method, of class DictionaryTreeMapImpl.
     */
    @Test
    public void testGetLongestWords() {
        dict.insert(words[0]);
        dict.insert(words[1]);
        assertTrue(dict.getLongestWords().isEmpty());

        dict.insert(words[2]);
        assertTrue(dict.getLongestWords().size() == 1);
        assertTrue(dict.getLongestWords().get(0).equals(words[2]));

        dict.insert(words[3]);
        dict.insert(words[2]);
        assertTrue(dict.getLongestWords().size() == 1);
        assertTrue(dict.getLongestWords().get(0).equals(words[3]));

        dict.insert(words[3]);
        assertTrue(dict.getLongestWords().size() == 1);
        assertTrue(dict.getLongestWords().get(0).equals(words[3]));

        dict.insert(words[5]);
        assertTrue(dict.getLongestWords().size() == 2);
        assertTrue(dict.getLongestWords().contains(words[3]));
        assertTrue(dict.getLongestWords().contains(words[5]));

        dict.insert(words[4]);
        assertTrue(dict.getLongestWords().size() == 1);
        assertTrue(dict.getLongestWords().get(0).equals(words[4]));
    }
}
