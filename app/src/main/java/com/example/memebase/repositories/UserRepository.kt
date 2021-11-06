package com.example.memebase.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.memebase.db.AppDatabase
import com.example.memebase.db.Dao
import com.example.memebase.models.userModels.Users

class UserRepository(private val userDao: Dao) {


    var loginDatabase: AppDatabase? = null
    var readAllData: LiveData<Users>? = null

    fun initializeDB(context: Context) : AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    fun getLoginDetails(context: Context, username: String,password:String) : LiveData<Users>? {
        loginDatabase = initializeDB(context)
        readAllData = loginDatabase!!.getDao().readAllData(username,password)
        return readAllData
    }

    fun insertUserInfo(context: Context, entity: Users){
        loginDatabase = initializeDB(context)
        userDao.registerUser(entity)

    }

}