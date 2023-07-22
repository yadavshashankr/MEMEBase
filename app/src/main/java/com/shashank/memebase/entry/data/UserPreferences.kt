package com.shashank.memebase.entry.data

import com.shashank.memebase.entry.models.AuthenticationResponse
import com.shashank.memebase.entry.models.RegisterRequest

interface UserPreferences {

    fun saveAuthenticatedUser(authenticatedUser: AuthenticationResponse?)

    fun getAuthenticatedUser() : AuthenticationResponse?

    fun verifyUserCredentials(email : String, password : String) : Boolean

    fun clearPreferences()

    fun saveUser(registerRequest: RegisterRequest) : Boolean

    fun isUserLoggedIn() : Boolean
}