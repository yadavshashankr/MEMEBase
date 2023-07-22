package com.shashank.memebase.networking.usecases.domain

interface TaskyLoader {
    suspend fun setLoading(isLoading : Boolean)
}