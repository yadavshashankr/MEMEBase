package com.example.memebase.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memebase.R
import com.example.memebase.adapter.MemeRecyclerAdapter

import com.example.memebase.viewModels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var viewModel: MainActivityViewModel? = null
    private var memeRecyclerAdapter: MemeRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView().also {
            loadAPIData()
        }

    }

    private fun initRecyclerView() {

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {

         layoutManager = LinearLayoutManager(this@MainActivity)

         val decoration = DividerItemDecoration(applicationContext,
             DividerItemDecoration.VERTICAL)
         addItemDecoration(decoration)

         memeRecyclerAdapter= MemeRecyclerAdapter()
         adapter= memeRecyclerAdapter

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadAPIData(){
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel?.getMemeListObserver()?.observe(this, {
            if (it != null) {
                memeRecyclerAdapter?.memeListData = it.data.memes
                memeRecyclerAdapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel?.makeApiCall()

    }




}