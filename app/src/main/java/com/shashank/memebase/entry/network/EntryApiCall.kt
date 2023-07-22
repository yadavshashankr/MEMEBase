package com.shashank.memebase.entry.network

import com.shashank.memebase.entry.models.AuthenticationRequest
import com.shashank.memebase.entry.models.AuthenticationResponse
import com.shashank.memebase.entry.models.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EntryApiCall {

    @POST("/register")
    suspend fun registration(@Body registerModel: RegisterRequest) : Response<Void>

    @POST("/login")
    suspend fun login(@Body authenticationModel: AuthenticationRequest) : Response<AuthenticationResponse>
}