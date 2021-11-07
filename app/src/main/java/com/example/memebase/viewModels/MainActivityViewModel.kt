package com.example.memebase.viewModels

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.memebase.activities.SelectVideoActivity
import com.example.memebase.models.memesModels.MemeModel

import com.example.memebase.repositories.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: NetworkRepository, application: Application) : AndroidViewModel(application) {

private val applicationContext by lazy { application.applicationContext }

    fun getMemeListObserver(): LiveData<MemeModel>? {
    return repository.getAllMemes()
}

    fun getProgressBar(): LiveData<Boolean>{
        return repository.progressDialog
    }

    fun makeApiCall(){
        repository.makeApiCall()
    }


}




