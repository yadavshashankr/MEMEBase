package com.shashank.memebase.meme.network

import com.shashank.memebase.meme.memesModels.MemeModel

import retrofit2.Response
import retrofit2.http.GET

interface RetroService {
    @GET("get_memes")
    suspend fun getMemeListApi(): Response<MemeModel>
}