package com.shashank.memebase.meme.repositories

import androidx.lifecycle.LiveData
import com.shashank.memebase.meme.memesModels.MemeModel

interface NetworkRepository {
   suspend fun getAllMemes() : MemeModel

    fun insertRecord(memeModel: MemeModel)

    fun makeApiCall()

    fun getProgressDlg() : LiveData<Boolean>
}