package com.garinyan.labs.utils;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class StringUtilsTest extends TestCase {

    @Test
    public void testGetFileName() {
        String nullFileName = null;
        String emptyFileName = "";
        String regularFileName = "/home/gyyan";
        String regularFileName2 = "/home/gyyan/testFileName";
        String directoryName = "/home/gyyan/";
        String fileNameWithSpace = "/home/gyyan/test file name";
        String fileNameWithSpaceAtEnd = "/home/gyyan/testFileName ";
        assertEquals(null, StringUtils.getFileName(nullFileName));
        assertEquals("", StringUtils.getFileName(emptyFileName));
        assertEquals("gyyan", StringUtils.getFileName(regularFileName));
        assertEquals("testFileName", StringUtils.getFileName(regularFileName2));
        assertEquals("", StringUtils.getFileName(directoryName));
        assertEquals("test file name", StringUtils.getFileName(fileNameWithSpace));
        assertEquals("testFileName ", StringUtils.getFileName(fileNameWithSpaceAtEnd));
    }
}
