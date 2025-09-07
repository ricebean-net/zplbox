package net.ricebean.zplbox.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimesTest {

    @Test
    public void duration2readable_1() throws Exception {

        // arrange
        long duration = (23 * 24 * 3600 + 2 * 60 + 1) * 1000 + 12; // 23d 0h 2m 1s

        // act
        String result = Times.duration2readable(duration);

        // assert
        assertEquals("23d 00h 02m 01s", result, "Duration is wrong.");
    }

    @Test
    public void duration2readable_2() throws Exception {

        // arrange
        long duration = (2 * 24 * 3600 + 22 * 3600 + 42 * 60 + 31) * 1000 + 361; // 2d 22h 42m 31s

        // act
        String result = Times.duration2readable(duration);

        // assert
        assertEquals("2d 22h 42m 31s", result, "Duration is wrong.");
    }

    @Test
    public void duration2readable_3() throws Exception {

        // arrange
        long duration = (6 * 3600 + 42 * 60 + 31) * 1000 + 361; // 0d 6h 42m 31s

        // act
        String result = Times.duration2readable(duration);

        // assert
        assertEquals("06h 42m 31s", result, "Duration is wrong.");
    }

    @Test
    public void duration2readable_4() throws Exception {

        // arrange
        long duration = (10 * 24 * 3600 + 22 * 3600 + 42 * 60 + 31) * 1000 + 361; // 10d 22h 42m 31s

        // act
        String result = Times.duration2readable(duration);

        // assert
        assertEquals("10d 22h 42m 31s", result, "Duration is wrong.");
    }

}