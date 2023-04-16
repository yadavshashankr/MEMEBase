package com.example.memebase.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.example.memebase.models.memesModels.MemeModel
import com.example.memebase.models.memesModels.Memes

import com.example.memebase.repositories.NetworkRepository
import com.example.memebase.utils.Tools.Companion.saveImageToStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: NetworkRepository, application: Application) : AndroidViewModel(application) {


    fun getMemeListObserver(): LiveData<MemeModel> {
    return repository.getAllMemes()
}

    fun getProgressBar(): LiveData<Boolean>{
        return repository.progressDialog
    }

    fun makeApiCall(){
        Executors.newSingleThreadExecutor().execute {
            repository.makeApiCall()
        }
    }

    fun downloadMeme(context: Context, memes: Memes){
        Executors.newSingleThreadExecutor().execute {
            val bitmap = Glide.with(context.applicationContext).asBitmap().load(memes.url).into(memes.width, memes.height.toInt()).get()
            saveImageToStorage(context, bitmap, memes.name.replace(" ", "_"))
        }
    }
}




