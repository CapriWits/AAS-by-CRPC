package me.noctambulist.aasweb.common.util;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/21 23:26
 */
class MD5UtilsTest {

    @Test
    public void testEmptyString() throws NoSuchAlgorithmException {
        String input = "";
        String expected = "d41d8cd98f00b204e9800998ecf8427e";
        String actual = MD5Utils.encode(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testEncode() throws NoSuchAlgorithmException {
        String input = "Hello, world!";
        String expected = "6cd3556deb0da54bca060b4c39479839";
        String actual = MD5Utils.encode(input);
        assertEquals(expected, actual);
    }

}