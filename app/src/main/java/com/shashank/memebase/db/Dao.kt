package com.shashank.memebase.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shashank.memebase.globals.ApplicationConstant
import com.shashank.memebase.models.memesModels.MemeModel


@Dao
interface Dao {
    @Query("SELECT * FROM ${ApplicationConstant.TABLE_NAME_MEME_MODEL}")
    fun getAllMemes(): LiveData<MemeModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecords(memeData: MemeModel)

    @Query("DELETE FROM ${ApplicationConstant.TABLE_NAME_MEME_MODEL}" )
    fun deleteAllRecords()
}