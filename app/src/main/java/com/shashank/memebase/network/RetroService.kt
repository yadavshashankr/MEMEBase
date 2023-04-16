package com.shashank.memebase.network

import com.shashank.memebase.models.memesModels.MemeModel

import io.reactivex.Observable
import retrofit2.http.GET

interface RetroService {

    @GET("get_memes")
    fun getMemeListApi(): Observable<MemeModel>
}