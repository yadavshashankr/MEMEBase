package com.example.memebase.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.memebase.models.memesModels.MemeModel

import com.example.memebase.repositories.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: NetworkRepository) : ViewModel() {



fun getMemeListObserver(): LiveData<MemeModel> {
    return repository.getAllMemes()
}



    fun makeApiCall(){
        repository.makeApiCall()
    }

}




