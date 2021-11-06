package com.example.memebase.network

import com.example.memebase.models.memesModels.MemeModel

import io.reactivex.Observable
import retrofit2.http.GET

interface RetroService {

    @GET("get_memes")
    fun getMemeListApi(): Observable<MemeModel>
}