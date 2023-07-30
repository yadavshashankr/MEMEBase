package com.shashank.memebase.meme.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.shashank.memebase.meme.memesModels.MemeModel
import com.shashank.memebase.meme.memesModels.Memes
import com.shashank.memebase.meme.repositories.NetworkRepository

import com.shashank.memebase.meme.storage.domain.SaveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class MemeActivityViewModel @Inject constructor(private val repository: NetworkRepository, application: Application, private val saveData: SaveData) : AndroidViewModel(application) {
    private var mutableMemeListLiveData = MutableLiveData<MemeModel?>()
    private var memeListLiveData : LiveData<MemeModel?> = mutableMemeListLiveData
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getMemeListObserver(): LiveData<MemeModel?> {
        scope.launch {
            val memes = repository.getAllMemes()
            mutableMemeListLiveData.postValue(memes)
        }
        return memeListLiveData
    }

    fun downloadMeme(context: Context, memes: Memes){
        Executors.newSingleThreadExecutor().execute {
            val bitmap = Glide.with(context.applicationContext).asBitmap().load(memes.url).into(memes.width as Int, memes.height?.toInt() as Int).get()
            saveData.saveImageToStorage(bitmap, memes.name?.replace(" ", "_") as String)
        }
    }
}




