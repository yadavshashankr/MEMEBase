package com.shashank.memebase.video_compressor.ui

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.transition.Slide
import com.shashank.memebase.R
import com.shashank.memebase.databinding.FragmentVideoSelectedBinding
import com.shashank.memebase.MainActivity
import com.shashank.memebase.globals.Constants
import com.shashank.memebase.meme.storage.SaveDataImpl
import com.shashank.memebase.video_compressor.viewModels.VideoSelectedActivityViewModel
import com.shashank.memebase.usecases.FragmentInflaterImpl
import com.shashank.memebase.usecases.domain.FragmentInflater
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoSelectedFragment : Fragment(), FragmentInflater by FragmentInflaterImpl() {
    private lateinit var binding: FragmentVideoSelectedBinding
    private val viewModel: VideoSelectedActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_video_selected, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentManager(activity?.supportFragmentManager as FragmentManager)

        setToolbarAndFab()
        Constants.VideoProperties.uri.let { viewModel.setVideo(it as Uri) }

        viewModel.videoUri.observe(viewLifecycleOwner) { uri ->
            binding.videoView.setVideoURI(uri)
            binding.videoView.start()
        }

        viewModel.compressing.observe(viewLifecycleOwner) {
            if (it) binding.progressCircular.show()
            else binding.progressCircular.hide()
        }

        viewModel.done.observe(viewLifecycleOwner) {
            if (it != null) {
                val parentActivity = (activity as MainActivity)
                Toast.makeText(parentActivity, Environment.DIRECTORY_MOVIES + "/MEMEBase/"+ SaveDataImpl(parentActivity.applicationContext).getFileName(viewModel.videoUri.value as Uri), Toast.LENGTH_LONG).show()
                Constants.VideoProperties.uri = viewModel.videoUri.value
                parentActivity.startFragment(CompressedVideoFragment.getInstance())
            }
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
        private lateinit var agendaFragment: VideoSelectedFragment

        @JvmStatic
        fun getInstance(): VideoSelectedFragment {
            agendaFragment = VideoSelectedFragment()
            agendaFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
            }
            return agendaFragment
        }
    }
}