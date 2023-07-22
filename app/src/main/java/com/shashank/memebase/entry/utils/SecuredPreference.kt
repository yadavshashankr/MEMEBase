package com.shashank.memebase.entry.utils

interface SecuredPreference {

    suspend fun generateSecuredPreference()

    suspend fun setAccessToken(accessToken: String?)

    suspend fun getAccessToken() : String

}