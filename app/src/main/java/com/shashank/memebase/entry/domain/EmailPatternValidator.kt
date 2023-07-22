package com.shashank.memebase.entry.domain

interface EmailPatternValidator {
    fun isValidEmailPattern(email : CharSequence) : Boolean
}