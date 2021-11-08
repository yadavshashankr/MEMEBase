package com.example.memebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.memebase.R
import com.example.memebase.databinding.ActivityCompressedVideoBinding
import com.example.memebase.viewModels.CompressedVideoActivityViewModel

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