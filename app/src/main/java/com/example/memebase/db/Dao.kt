package com.example.memebase.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memebase.globals.ApplicationConstant
import com.example.memebase.models.memesModels.MemeModel
import com.example.memebase.models.userModels.Users


@Dao
interface Dao {

    @Query("SELECT * FROM ${ApplicationConstant.TABLE_NAME_MEME_MODEL}")
    fun getAllMemes(): LiveData<MemeModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecords(memeData: MemeModel)

    @Query("DELETE FROM ${ApplicationConstant.TABLE_NAME_MEME_MODEL}" )
    fun deleteAllRecords()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerUser(user: Users)

    @Query("SELECT * FROM ${ApplicationConstant.TABLE_NAME_USERS} WHERE userId LIKE :userid AND password LIKE :password")
    fun readAllData(userid: String, password: String): LiveData<Users>

}