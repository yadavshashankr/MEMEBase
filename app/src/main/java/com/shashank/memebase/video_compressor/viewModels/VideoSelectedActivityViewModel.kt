package com.shashank.memebase.video_compressor.viewModels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.shashank.memebase.meme.storage.domain.SaveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URI
import javax.inject.Inject


@HiltViewModel
class VideoSelectedActivityViewModel @Inject constructor(application : Application, private val saveData: SaveData): AndroidViewModel(application) {
    private val mutableVideoUri = MutableLiveData<Uri>()
    val videoUri: LiveData<Uri> = mutableVideoUri
    private val applicationContext: Context by lazy { application.applicationContext }
    private val mutableError = MutableLiveData("")
    private val mutableCompressing = MutableLiveData(false)
    val compressing: LiveData<Boolean> = mutableCompressing
    private val mutableDone = MutableLiveData<Uri>()
    val done: LiveData<Uri> = mutableDone
    private var file: File? = null

    @SuppressLint("Recycle")
    fun setVideo(uri: Uri) {
        mutableVideoUri.value = uri
        file?.delete()
        val stream = applicationContext.contentResolver.openInputStream(uri)
        file = File.createTempFile(saveData.getFileName(uri), ".mp4", applicationContext.filesDir)
        stream?.let { file?.writeBytes(it.readBytes()) }
    }

    fun compress() {
        mutableError.value = ""
        mutableCompressing.value = true

        val outputPath = File(URI("file:///storage/emulated/0/"+Environment.DIRECTORY_MOVIES + "/MEMEBase/").path)
        if(!outputPath.exists()){
            outputPath.mkdirs()
        }
        val fileName = "compressed_${saveData.getFileName(file?.toUri() as Uri)}"
        val outputFile = File.createTempFile(fileName, ".mp4", outputPath)
        viewModelScope.launch {
            when (withContext(Dispatchers.IO) { FFmpeg.execute("-y -noautorotate -i ${file?.path} -s 1280x720 -metadata:s:v:0 rotate=90 -b:v 150k -vcodec mpeg4 -acodec copy $outputFile") }) {
                Config.RETURN_CODE_SUCCESS -> {
                    file?.delete()
                    mutableCompressing.value = false
                    mutableDone.value = outputFile.toUri()
                }
                Config.RETURN_CODE_CANCEL -> {
                    mutableError.postValue("Compression Cancelled")
                    mutableCompressing.value = false
                }
                else -> {
                    mutableError.postValue("Compression Failed")
                    mutableCompressing.value = false
                }
            }
        }
    }
}