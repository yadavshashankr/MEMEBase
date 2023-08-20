package com.shashank.memebase.video_compressor.ui

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
import com.shashank.memebase.databinding.FragmentSelectVideoBinding
import com.shashank.memebase.MainActivity
import com.shashank.memebase.video_compressor.viewModels.SelectVideoActivityViewModel
import com.shashank.memebase.usecases.FragmentInflaterImpl
import com.shashank.memebase.usecases.domain.FragmentInflater
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectVideoFragment : Fragment(), FragmentInflater by FragmentInflaterImpl() {
    private val activityViewModel: SelectVideoActivityViewModel by viewModels()
    private lateinit var binding: FragmentSelectVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_select_video, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = activityViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentManager(activity?.supportFragmentManager as FragmentManager)

        setToolbarAndFab()

        activityViewModel.showSelectVideo.observe(viewLifecycleOwner) {
            if (it) {
                getVideo()
                activityViewModel.doneSelection()
            }
        }

    }

    private fun getVideo() {
        val parentActivity = requireActivity() as MainActivity
        parentActivity.videoFetcher.launch("video/*")
    }

    private fun setToolbarAndFab() {
        val parentActivity = requireActivity() as MainActivity
        parentActivity.setTitle((activity as MainActivity).getString(R.string.video_compressor))

        parentActivity.showFAB(R.drawable.fab_plus, "memeDialog")
        parentActivity.setFabLocation(true)
        parentActivity.setToolbarHeight(false)
    }

    companion object {
        private lateinit var agendaFragment: SelectVideoFragment

        @JvmStatic
        fun getInstance(): SelectVideoFragment {
            agendaFragment = SelectVideoFragment()
            agendaFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
            }
            return agendaFragment
        }
    }
}