package vkrajn.scraper.utils;

/**
 * An integer class which is initialized to 1, can never contain null.
 * 
 * @author Vojtech Krajnansky
 * @version 07/19/2017
 */
public class MutableInt {
        
        // Attributes
        private int value;

        // Constructors
        public MutableInt() {
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
