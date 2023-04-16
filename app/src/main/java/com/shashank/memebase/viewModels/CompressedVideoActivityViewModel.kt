package com.shashank.memebase.viewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompressedVideoActivityViewModel: ViewModel() {

    private val mutableIsPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> = mutableIsPlaying
    private val mutableVideoUri = MutableLiveData<Uri>()
    val videoUri: LiveData<Uri> = mutableVideoUri

    fun setVideo(uri: Uri) {
        mutableVideoUri.value = uri
        mutableIsPlaying.value = true
    }

    fun togglePause() {
        mutableIsPlaying.value = mutableIsPlaying.value?.not()
    }
}