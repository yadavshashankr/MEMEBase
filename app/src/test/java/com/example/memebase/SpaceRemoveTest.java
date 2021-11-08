package com.example.memebase;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import com.example.memebase.utils.EmailValidator;
import com.example.memebase.utils.Tools;

import org.junit.Test;

public class SpaceRemoveTest {

    @Test
    public void spaceRemovalCharTest() {
        assertTrue(Tools.Companion.isSpaceInString("abcd"));
    }
    @Test
    public void spaceRemovalNumTest() {
        assertTrue(Tools.Companion.isSpaceInString("123456"));
    }
    @Test
    public void spaceRemovalStringTest() {
        assertFalse(Tools.Companion.isSpaceInString("n m j k l"));
    }
}
