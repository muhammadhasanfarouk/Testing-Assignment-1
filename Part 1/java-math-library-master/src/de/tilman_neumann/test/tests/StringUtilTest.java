package de.tilman_neumann.test.junit;


import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

import de.tilman_neumann.util.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilTest {

    // =========================
    // repeat()
    // =========================

    @Test
    void testRepeat_TC1() {
        Assertions.assertEquals("abcabc", StringUtil.repeat("abc", 2));
    }

    @Test
    void testRepeat_TC2() {
        Assertions.assertEquals("abc", StringUtil.repeat("abc", 1));
    }

    @Test
    void testRepeat_TC3() {
        Assertions.assertEquals("", StringUtil.repeat("", 2));
    }

    @Test
    void testRepeat_TC4() {
        Assertions.assertNull(StringUtil.repeat("abc", 0));
    }

    @Test
    void testRepeat_TC5() {
        Assertions.assertNull(StringUtil.repeat("abc", -1));
    }

    @Test
    void testRepeat_TC6() {
        Assertions.assertNull(StringUtil.repeat(null, 2));
    }

    @Test
    void testRepeat_TC7() {
        Assertions.assertEquals("aa", StringUtil.repeat("a", 2));
    }

    // =========================
    // formatLeft()
    // =========================

    @Test
    void testFormatLeft_TC1() {
        Assertions.assertEquals("abc456", StringUtil.formatLeft("abc", "123456"));
    }

    @Test
    void testFormatLeft_TC2() {
        Assertions.assertEquals("a", StringUtil.formatLeft("a", "1"));
    }

    @Test
    void testFormatLeft_TC3() {
        Assertions.assertEquals("abcd", StringUtil.formatLeft("abcd", ""));
    }

    @Test
    void testFormatLeft_TC4() {
        Assertions.assertEquals("12345", StringUtil.formatLeft(null, "12345"));
    }

    @Test
    void testFormatLeft_TC5() {
        Assertions.assertEquals("abcd", StringUtil.formatLeft("abcd", null));
    }

    @Test
    void testFormatLeft_TC6() {
        Assertions.assertEquals("1", StringUtil.formatLeft("", "1"));
    }

    @Test
    void testFormatLeft_TC7() {
        Assertions.assertEquals("abcd", StringUtil.formatLeft("abcd", "123"));
    }

    // =========================
    // formatRight()
    // =========================

    @Test
    void testFormatRight_TC1() {
        Assertions.assertEquals("123abc", StringUtil.formatRight("abc", "123456"));
    }

    @Test
    void testFormatRight_TC2() {
        Assertions.assertEquals("a", StringUtil.formatRight("a", "1"));
    }

    @Test
    void testFormatRight_TC3() {
        Assertions.assertEquals("abcd", StringUtil.formatRight("abcd", ""));
    }

    @Test
    void testFormatRight_TC4() {
        Assertions.assertEquals("12345", StringUtil.formatRight(null, "12345"));
    }

    @Test
    void testFormatRight_TC5() {
        Assertions.assertEquals("abcd", StringUtil.formatRight("abcd", null));
    }

    @Test
    void testFormatRight_TC6() {
        Assertions.assertEquals("1", StringUtil.formatRight("", "1"));
    }

    @Test
    void testFormatRight_TC7() {
        Assertions.assertEquals("abcd", StringUtil.formatRight("abcd", "123"));
    }
}
