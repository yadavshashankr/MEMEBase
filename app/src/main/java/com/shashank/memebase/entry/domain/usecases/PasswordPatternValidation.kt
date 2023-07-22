package com.shashank.memebase.entry.domain.usecases

import com.shashank.memebase.globals.Constants.FieldValidationsProperties.MAX_CHARACTERS_FOR_PASSWORD
import com.shashank.memebase.globals.Constants.FieldValidationsProperties.MIN_CHARACTERS_FOR_PASSWORD
import javax.inject.Inject

class PasswordPatternValidation @Inject constructor() {
    fun isPasswordPatternValid(password: CharSequence): Boolean {
        val hasValidLength = password.length in (MIN_CHARACTERS_FOR_PASSWORD..MAX_CHARACTERS_FOR_PASSWORD)
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasAnyDigits = password.any { it.isDigit() }

        return hasValidLength && hasUpperCase && hasLowerCase && hasAnyDigits
    }
}