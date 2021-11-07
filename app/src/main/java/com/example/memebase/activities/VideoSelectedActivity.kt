package com.example.memebase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.memebase.R
import com.example.memebase.databinding.ActivityVideoSelectedBinding
import com.example.memebase.viewModels.VideoSelectedActivityViewModel

class VideoSelectedActivity : AppCompatActivity() {
    private val viewModel: VideoSelectedActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityVideoSelectedBinding>(
            this,
            R.layout.activity_video_selected
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        intent?.data?.let { viewModel.setVideo(it) }

        viewModel.videoUri.observe(this) { uri ->
            binding.videoView.setVideoURI(uri)
            binding.videoView.start()
        }

        viewModel.compressing.observe(this) {
            if (it) binding.progressCircular.show()
            else binding.progressCircular.hide()
        }

        viewModel.done.observe(this) {
            if (it != null) {
                startActivity(Intent(this, CompressedVideoActivity::class.java).apply {
                    data = viewModel.videoUri.value
                })
                finish()
            }
        }
    }
}