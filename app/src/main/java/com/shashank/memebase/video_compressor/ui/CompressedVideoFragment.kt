package com.shashank.memebase.video_compressor.ui

import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.transition.Slide
import com.shashank.memebase.R
import com.shashank.memebase.databinding.FragmentCompressedVideoBinding
import com.shashank.memebase.MainActivity
import com.shashank.memebase.globals.Constants
import com.shashank.memebase.video_compressor.viewModels.CompressedVideoActivityViewModel
import com.shashank.memebase.usecases.FragmentInflaterImpl
import com.shashank.memebase.usecases.domain.FragmentInflater
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompressedVideoFragment : Fragment(), FragmentInflater by FragmentInflaterImpl() {
    val viewModel: CompressedVideoActivityViewModel by viewModels()
    private lateinit var binding: FragmentCompressedVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_compressed_video, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentManager(activity?.supportFragmentManager as FragmentManager)

        setToolbarAndFab()
        Constants.VideoProperties.uri.let { viewModel.setVideo(it as Uri) }

        viewModel.videoUri.observe(viewLifecycleOwner) {
            binding.videoView.setVideoURI(it)
            binding.videoView.start()
        }

        viewModel.isPlaying.observe(viewLifecycleOwner) {
            if (it) binding.videoView.start()
            else binding.videoView.pause()
        }
    }

    private fun setToolbarAndFab() {
        val parentActivity = requireActivity() as MainActivity
        parentActivity.setTitle((activity as MainActivity).getString(R.string.video_compressor))

        parentActivity.showFAB(R.drawable.fab_plus, "memeDialog")
        parentActivity.setFabLocation(true)
        parentActivity.setToolbarHeight(false)
    }

    companion object {
        private lateinit var agendaFragment: CompressedVideoFragment

        @JvmStatic
        fun getInstance(): CompressedVideoFragment {
            agendaFragment = CompressedVideoFragment()
            agendaFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
            }
            return agendaFragment
        }
    }
}