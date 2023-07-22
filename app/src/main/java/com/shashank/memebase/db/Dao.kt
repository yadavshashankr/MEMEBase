package com.shashank.memebase.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shashank.memebase.globals.Constants
import com.shashank.memebase.meme.memesModels.MemeModel


@Dao
interface Dao {
    @Query("SELECT * FROM ${Constants.ApiProperties.TABLE_NAME_MEME_MODEL}")
    suspend fun getAllMemes(): MemeModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecords(memeData: MemeModel)

    @Query("DELETE FROM ${Constants.ApiProperties.TABLE_NAME_MEME_MODEL}" )
    fun deleteAllRecords()
}