package com.shashank.memebase.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.shashank.memebase.R
import com.shashank.memebase.databinding.ActivityVideoSelectedBinding
import com.shashank.memebase.storage.SaveDataImpl
import com.shashank.memebase.viewModels.VideoSelectedActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                Toast.makeText(this, Environment.DIRECTORY_MOVIES + "/MEMEBase/"+ SaveDataImpl(applicationContext).getFileName(viewModel.videoUri.value as Uri), Toast.LENGTH_LONG).show()
                startActivity(Intent(this, CompressedVideoActivity::class.java).apply {
                    data = viewModel.videoUri.value
                })
                finish()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}