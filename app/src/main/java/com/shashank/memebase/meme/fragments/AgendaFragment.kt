package com.shashank.memebase.meme.fragments

import android.Manifest
import android.content.Intent
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
import com.likethesalad.android.aaper.api.EnsurePermissions
import com.shashank.memebase.R
import com.shashank.memebase.meme.adapters.MemeRecyclerAdapter
import com.shashank.memebase.databinding.ActivityMemeBinding
import com.shashank.memebase.entry.MainActivity
import com.shashank.memebase.meme.ui.SelectVideoActivity
import com.shashank.memebase.meme.viewModels.MemeActivityViewModel
import com.shashank.memebase.usecases.FragmentInflaterImpl
import com.shashank.memebase.usecases.domain.FragmentInflater
import com.shashank.memebase.utils.NetworkCheck
import com.shashank.memebase.utils.Tools
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendaFragment : Fragment(), FragmentInflater by FragmentInflaterImpl() {
    private var memeRecyclerAdapter: MemeRecyclerAdapter? = null
    private val viewModel: MemeActivityViewModel by viewModels()
    lateinit var bindingA: ActivityMemeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingA  = DataBindingUtil.inflate(inflater, R.layout.activity_meme, container, false)
        bindingA.lifecycleOwner = this
        return bindingA.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentManager(activity?.supportFragmentManager as FragmentManager)

        setToolbarAndFab()
        bindingA.viewModel = viewModel
        initRecyclerView()

        viewModel.getProgressDlg().observe(viewLifecycleOwner){
            if (it){
                Tools.setViewGone(bindingA.recyclerView)
            }else{
                Tools.setViewVisible(bindingA.recyclerView)
            }
        }

        viewModel.getMemeListObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                Tools.setViewVisible(bindingA.recyclerView)
                Tools.setViewGone(bindingA.ivNoInternet)
                memeRecyclerAdapter?.memeListData = it.data.memes
                memeRecyclerAdapter?.viewModel = viewModel
                memeRecyclerAdapter?.notifyDataSetChanged()
            } else {
                if (!NetworkCheck.verifyAvailableNetwork(activity as MainActivity)) {
                    Tools.setViewVisible(bindingA.ivNoInternet)
                    Tools.setViewGone(bindingA.recyclerView)
                }
            }
        }
    }

    private fun setToolbarAndFab() {
        val parentActivity = requireActivity() as MainActivity
        parentActivity.setTitle((activity as MainActivity).getString(R.string.app_name))

        parentActivity.showFAB(R.drawable.fab_plus, "agendaDialog")
        parentActivity.setFabLocation(true)
        parentActivity.setToolbarHeight(false)
    }

    @EnsurePermissions(permissions = [Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE])
    private fun videoComp() {

        val parentActivity = (activity as MainActivity)
        parentActivity.startActivity(Intent(activity, SelectVideoActivity::class.java))
    }

    private fun initRecyclerView() {
        bindingA.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(activity)
            memeRecyclerAdapter= MemeRecyclerAdapter()
            this.adapter = memeRecyclerAdapter
        }
    }

    companion object {

        private lateinit var agendaFragment: AgendaFragment

        @JvmStatic
        fun getInstance(): AgendaFragment {
            agendaFragment = AgendaFragment()
            agendaFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
            }
            return agendaFragment
        }
    }
}