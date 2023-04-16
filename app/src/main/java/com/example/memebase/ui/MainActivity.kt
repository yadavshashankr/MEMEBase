package com.example.memebase.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memebase.R
import com.example.memebase.adapter.MemeRecyclerAdapter

import com.example.memebase.viewModels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


import androidx.databinding.DataBindingUtil
import com.example.memebase.databinding.ActivityMainBinding
import com.example.memebase.utils.NetworkCheck
import com.example.memebase.utils.Tools.Companion.setViewGone
import com.example.memebase.utils.Tools.Companion.setViewVisible
import com.likethesalad.android.aaper.api.EnsurePermissions


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var memeRecyclerAdapter: MemeRecyclerAdapter? = null
    private val viewModel: MainActivityViewModel by viewModels()
    var bindingA: ActivityMainBinding?= null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingA = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )

        bindingA?.lifecycleOwner = this
        bindingA?.viewModel = viewModel
        initRecyclerView()

        viewModel.getProgressBar().observe(this){
            if (it){
                setViewVisible(bindingA?.progressCircular)
                setViewGone(bindingA?.recyclerView)
            }else{
                setViewVisible(bindingA?.recyclerView)
                setViewGone(bindingA?.progressCircular)
            }
        }

        viewModel.getMemeListObserver().observe(this) {
            if (it != null) {
                setViewVisible(bindingA?.recyclerView)
                setViewGone(bindingA?.ivNoInternet)
                memeRecyclerAdapter?.memeListData = it.data.memes
                memeRecyclerAdapter?.viewModel = viewModel
                memeRecyclerAdapter?.notifyDataSetChanged()
            } else {
                if (!NetworkCheck.verifyAvailableNetwork(this)) {
                    setViewVisible(bindingA?.ivNoInternet)
                    setViewGone(bindingA?.recyclerView)
                }
            }
        }
        bindingA?.fab?.setOnClickListener{
            videoComp()
        }

        loadAPIData()
    }
    @EnsurePermissions(permissions = [Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE])
    private fun videoComp() {
        startActivity(Intent(this, SelectVideoActivity::class.java))
    }

    private fun initRecyclerView() {
        bindingA?.recyclerView.apply {
            this?.layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
            this?.addItemDecoration(decoration)
            memeRecyclerAdapter= MemeRecyclerAdapter()
            this?.adapter = memeRecyclerAdapter
        }
    }
    private fun loadAPIData(){
         viewModel.makeApiCall()
    }
    override fun onBackPressed() {
        finish()
    }
}