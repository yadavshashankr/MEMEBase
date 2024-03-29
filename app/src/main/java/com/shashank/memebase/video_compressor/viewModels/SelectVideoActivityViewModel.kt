package com.shashank.memebase.video_compressor.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectVideoActivityViewModel : ViewModel() {
    private val mutableShowSelectVideo = MutableLiveData<Boolean>()
    val showSelectVideo: LiveData<Boolean> = mutableShowSelectVideo
    fun selectVideo() {
        mutableShowSelectVideo.postValue(true)
    }

    fun doneSelection() {
        mutableShowSelectVideo.value = false
    }
}