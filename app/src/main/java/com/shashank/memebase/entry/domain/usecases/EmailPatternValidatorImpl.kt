package com.shashank.memebase.entry.domain.usecases

import android.util.Patterns
import com.shashank.memebase.entry.domain.EmailPatternValidator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailPatternValidatorImpl @Inject constructor() : EmailPatternValidator {
    override fun isValidEmailPattern(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}