package com.hard;

import org.junit.Assert;
import org.junit.Test;

public class HashingTest {
    @Test
    public void should_be_the_same_hashcode_from_the_same_data() {
        Hashing hashing = new Hashing();

        int hashcode1 = hashing.hashcode("Hello World");

        for (int i = 0; i < 100; i++) {
            int hashcode2 = hashing.hashcode("Hello World");

            Assert.assertEquals(hashcode1, hashcode2);
        }
    }
}
