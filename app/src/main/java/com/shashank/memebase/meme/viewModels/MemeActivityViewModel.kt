package com.shashank.memebase.meme.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.shashank.memebase.meme.memesModels.MemeModel
import com.shashank.memebase.meme.memesModels.Memes
import com.shashank.memebase.meme.repositories.NetworkRepository

import com.shashank.memebase.meme.storage.domain.SaveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class MemeActivityViewModel @Inject constructor(private val repository: NetworkRepository, application: Application, private val saveData: SaveData) : AndroidViewModel(application) {

    var mutableMemeListLiveData = MutableLiveData<MemeModel>()
    fun getMemeListObserver(): LiveData<MemeModel> {
        viewModelScope.launch (Dispatchers.IO){
            val memes = repository.getAllMemes()
            mutableMemeListLiveData.postValue(memes)
        }
        return mutableMemeListLiveData
    }

    fun getProgressDlg(): LiveData<Boolean>{
        return repository.getProgressDlg()
    }

    fun downloadMeme(context: Context, memes: Memes){
        Executors.newSingleThreadExecutor().execute {
            val bitmap = Glide.with(context.applicationContext).asBitmap().load(memes.url).into(memes.width, memes.height.toInt()).get()
            saveData.saveImageToStorage(bitmap, memes.name.replace(" ", "_"))
        }
    }
}




