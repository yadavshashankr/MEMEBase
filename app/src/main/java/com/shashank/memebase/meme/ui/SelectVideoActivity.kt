package com.shashank.memebase.meme.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.shashank.memebase.R
import com.shashank.memebase.databinding.ActivitySelectVideoBinding
import com.shashank.memebase.meme.viewModels.SelectVideoActivityViewModel
import com.shashank.memebase.utils.Tools


class SelectVideoActivity : AppCompatActivity() {
    private val activityViewModel: SelectVideoActivityViewModel by viewModels()
    private val videoFetcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it == null) return@registerForActivityResult
        val fileDescriptor = applicationContext.contentResolver.openAssetFileDescriptor(it, "r")
        val fileSize = fileDescriptor!!.length /1000

        if (fileSize in 10001..49999){
            startActivity(Intent(this, VideoSelectedActivity::class.java).apply {
            data = it
        })
        }else{
            Tools.longToast(this, resources.getString(R.string.sizeSpec))
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySelectVideoBinding>(
            this,
            R.layout.activity_select_video
        )

        binding.viewModel = activityViewModel
        binding.lifecycleOwner = this
        activityViewModel.showSelectVideo.observe(this) {
            if (it) {
                getVideo()
                activityViewModel.doneSelection()
            }
        }
    }
    private fun getVideo() {
        videoFetcher.launch("video/*")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}