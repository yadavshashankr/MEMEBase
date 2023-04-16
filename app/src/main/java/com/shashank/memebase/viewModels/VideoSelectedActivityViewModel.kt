package com.shashank.memebase.viewModels

import android.app.Application
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class VideoSelectedActivityViewModel(application: Application): AndroidViewModel(application) {
    private val mutableVideoUri = MutableLiveData<Uri>()
    val videoUri: LiveData<Uri> = mutableVideoUri
    val bitRate = MutableLiveData<String>()
    val applicationContext by lazy { application.applicationContext }
    private val mutableError = MutableLiveData("")
    private val mutableCompressing = MutableLiveData(false)
    val error: LiveData<String> = mutableError
    val compressing: LiveData<Boolean> = mutableCompressing
    private val mutableDone = MutableLiveData<Uri>()
    val done: LiveData<Uri> = mutableDone
    private var file: File? = null

    fun setVideo(uri: Uri) {
        mutableVideoUri.value = uri
        file?.delete()
        val stream = applicationContext.contentResolver.openInputStream(uri)
        file = File.createTempFile("Original", ".mp4", applicationContext.filesDir)
        stream?.let { file?.writeBytes(it.readBytes()) }
    }

    fun compress() {
        mutableError.value = ""
        val rate = 128
        mutableCompressing.value = true
        val outputPath = applicationContext.getExternalFilesDir("CompressedVideos")
        val outputFile = File.createTempFile("compressed_${Date().time}", ".mp4", outputPath)
        viewModelScope.launch {
            val rc =
                withContext(Dispatchers.IO) { FFmpeg.execute("-y -i ${file?.path}  -b:v $rate ${outputFile.path}") }
            when (rc) {
                Config.RETURN_CODE_SUCCESS -> {
                    file?.delete()
                    mutableCompressing.value = false
                    mutableDone.value = outputFile.toUri()
                }
                Config.RETURN_CODE_CANCEL -> {
                    mutableError.value = "Compression Cancelled!"
                    mutableCompressing.value = false
                }
                else -> {
                    mutableError.value = "Compression Failed"
                    mutableCompressing.value = false
                }
            }
        }
    }
}