package com.shashank.memebase.meme.network

import com.shashank.memebase.meme.memesModels.MemeModel

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface RetroService {
    @GET
    fun getMemeListApi(@Url url: String): Observable<MemeModel?>
}