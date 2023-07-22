package com.shashank.memebase.meme.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.shashank.memebase.R
import com.shashank.memebase.databinding.ActivityCompressedVideoBinding
import com.shashank.memebase.meme.viewModels.CompressedVideoActivityViewModel

class CompressedVideoActivity : AppCompatActivity() {
    val viewModel: CompressedVideoActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCompressedVideoBinding>(
            this,
            R.layout.activity_compressed_video
        )
        intent.data?.let { viewModel.setVideo(it) }
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.videoUri.observe(this) {
            binding.videoView.setVideoURI(it)
            binding.videoView.start()
        }
        viewModel.isPlaying.observe(this) {
            if (it) binding.videoView.start()
            else binding.videoView.pause()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}