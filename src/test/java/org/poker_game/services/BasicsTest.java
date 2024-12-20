package org.poker_game.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicsTest {
    @Test
    void compare() {
        Basics basicTests = new Basics();
        int value = basicTests.compare(2, 1);

        Assertions.assertEquals(1, value);
    }

    @Test
    @DisplayName("First number is less than the second")
    void compare2() {
        Basics basicTests = new Basics();
        int value = basicTests.compare(2, 3);
        Assertions.assertEquals(-1, value);
    }

    @Test
    @DisplayName("First number is equal to the second")
    void compare3() {
        Basics basicTests = new Basics();
        int value = basicTests.compare(2, 2);
        Assertions.assertEquals(0, value);
    }

    @Test
    @DisplayName("Array sorted")
    void sortArray() {
        Basics basicTests = new Basics();
        int[] array = {5, 8, 3, 9, 1, 6};
        basicTests.sortArray(array);
        Assertions.assertArrayEquals(new int[]{1, 3, 5, 6, 8, 9}, array);
    }
}