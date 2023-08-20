package com.shashank.memebase.meme.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import com.shashank.memebase.R
import com.shashank.memebase.meme.adapters.MemeRecyclerAdapter
import com.shashank.memebase.databinding.ActivityMemeBinding
import com.shashank.memebase.MainActivity
import com.shashank.memebase.meme.memesModels.Memes
import com.shashank.memebase.video_compressor.viewModels.MemeActivityViewModel
import com.shashank.memebase.usecases.FragmentInflaterImpl
import com.shashank.memebase.usecases.domain.FragmentInflater
import com.shashank.memebase.utils.Tools
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemeFragment : Fragment(), FragmentInflater by FragmentInflaterImpl() {
    private var memeRecyclerAdapter: MemeRecyclerAdapter? = null
    private val viewModel: MemeActivityViewModel by viewModels()
    private lateinit var bindingA: ActivityMemeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingA  = DataBindingUtil.inflate(inflater, R.layout.activity_meme, container, false)
        bindingA.lifecycleOwner = this
        return bindingA.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentManager(activity?.supportFragmentManager as FragmentManager)

        setToolbarAndFab()
        bindingA.viewModel = viewModel
        initRecyclerView()

        viewModel.getMemeListObserver().observe(viewLifecycleOwner) {
            if (it?.data != null) {
                Tools.setViewVisible(bindingA.recyclerView)
                Tools.setViewGone(bindingA.ivNoInternet)
                memeRecyclerAdapter?.memeListData = it.data.memes as ArrayList<Memes>
                memeRecyclerAdapter?.viewModel = viewModel
                memeRecyclerAdapter?.notifyDataSetChanged()
            } else {
                Tools.setViewVisible(bindingA.ivNoInternet)
                Tools.setViewGone(bindingA.recyclerView)
            }
        }
    }

    private fun setToolbarAndFab() {
        val parentActivity = requireActivity() as MainActivity
        parentActivity.setTitle((activity as MainActivity).getString(R.string.app_name))

        parentActivity.showFAB(R.drawable.fab_plus, "compressorDialog")
        parentActivity.setFabLocation(true)
        parentActivity.setToolbarHeight(false)
    }

    private fun initRecyclerView() {
        bindingA.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(activity)
            memeRecyclerAdapter= MemeRecyclerAdapter()
            this.adapter = memeRecyclerAdapter
        }
    }

    companion object {

        private lateinit var agendaFragment: MemeFragment

        @JvmStatic
        fun getInstance(): MemeFragment {
            agendaFragment = MemeFragment()
            agendaFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
            }
            return agendaFragment
        }
    }
}