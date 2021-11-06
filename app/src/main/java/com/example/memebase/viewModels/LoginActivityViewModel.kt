package com.example.memebase.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.memebase.activities.LoginActivity
import com.example.memebase.db.AppDatabase
import com.example.memebase.db.Dao
import com.example.memebase.models.userModels.Users
import com.example.memebase.repositories.UserRepository

class LoginActivityViewModel(app: Application): AndroidViewModel(app) {
    var readAllData: LiveData<Users>? = null
    val repository: UserRepository

    init {
        val userDao = AppDatabase.getAppDBInstance(
            getApplication()
        ).getDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun getLoginDetails(context: Context, username: String, password:String) : LiveData<Users>? {

        readAllData = repository.getLoginDetails(context,username,password)
        return readAllData
    }

    fun insertUserInfo(context: Context, entity: Users){

        repository.insertUserInfo(context, entity)

    }

}