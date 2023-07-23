package com.shashank.memebase.meme.repositories

import com.shashank.memebase.meme.memesModels.MemeModel

interface NetworkRepository {
   suspend fun getAllMemes() : MemeModel?

    fun insertRecord(memeModel: MemeModel)

    suspend fun makeApiCall() : MemeModel?
}