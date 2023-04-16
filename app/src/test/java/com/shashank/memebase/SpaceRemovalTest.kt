package com.shashank.memebase

import com.shashank.memebase.utils.Tools
import com.shashank.memebase.utils.Tools.Companion.isSpaceInString
import junit.framework.TestCase
import org.junit.Test

class SpaceRemoveTest {
    @Test
    fun spaceRemovalCharTest() {
        TestCase.assertTrue(isSpaceInString("abcd"))
    }

    @Test
    fun spaceRemovalNumTest() {
        TestCase.assertTrue(isSpaceInString("123456"))
    }

    @Test
    fun spaceRemovalStringTest() {
        TestCase.assertFalse(isSpaceInString("n m j k l"))
    }
}