package com.shashank.memebase.entry.repositories

import com.shashank.memebase.entry.models.AuthenticationRequest
import com.shashank.memebase.entry.models.AuthenticationResponse
import com.shashank.memebase.entry.models.RegisterRequest

interface EntryRepository {
    suspend fun doLogin(authenticationRequest: AuthenticationRequest) : AuthenticationResponse?
    suspend fun doRegistration(registerRequest: RegisterRequest) : Boolean
}