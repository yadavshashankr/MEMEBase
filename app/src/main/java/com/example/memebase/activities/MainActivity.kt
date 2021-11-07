package com.example.memebase.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memebase.R
import com.example.memebase.adapter.MemeRecyclerAdapter
import com.example.memebase.utils.Tools

import com.example.memebase.viewModels.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.activity.result.ActivityResultCallback



import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.PermissionChecker.checkSelfPermission
import android.content.DialogInterface
import android.os.AsyncTask
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.memebase.databinding.ActivityLoginBinding
import com.example.memebase.databinding.ActivityMainBinding
import com.example.memebase.models.memesModels.MemeModel
import com.example.memebase.viewModels.LoginActivityViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.likethesalad.android.aaper.api.EnsurePermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private var memeRecyclerAdapter: MemeRecyclerAdapter? = null
    private var ivNoInternet: ImageView? = null
    private var recyclerView: RecyclerView? = null
    private var circularProgressIndicator: ProgressBar? = null
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

        viewModel.getMemeListObserver()?.observe(this, {
            if (it != null) {
                bindingA?.ivNoInternet?.visibility = View.GONE
                bindingA?.recyclerView?.visibility = View.VISIBLE
                memeRecyclerAdapter?.memeListData = it.data.memes
                memeRecyclerAdapter?.notifyDataSetChanged()
            } else {
                bindingA?.ivNoInternet?.visibility = View.VISIBLE
                bindingA?.recyclerView?.visibility = View.GONE
            }
        })
        viewModel.getProgressBar().observe(this){
            if (it){
                bindingA?.progressCircular?.visibility = View.VISIBLE
                bindingA?.recyclerView?.visibility = View.GONE
            }else{
                bindingA?.progressCircular?.visibility = View.GONE
                bindingA?.recyclerView?.visibility = View.VISIBLE
            }
        }
        loadAPIData()
        bindingA?.fab?.setOnClickListener{
            videoComp()
        }
    }
    @EnsurePermissions(permissions = [Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE])
    private fun videoComp() {
        startActivity(Intent(this, SelectVideoActivity::class.java))
    }


    private fun initRecyclerView() {

        bindingA?.recyclerView.apply {

         this?.layoutManager = LinearLayoutManager(this@MainActivity)

         val decoration = DividerItemDecoration(applicationContext,
             DividerItemDecoration.VERTICAL)
            this?.addItemDecoration(decoration)

         memeRecyclerAdapter= MemeRecyclerAdapter()
         this?.adapter = memeRecyclerAdapter

        }

    }


    fun loadAPIData(){
        Executors.newSingleThreadExecutor().execute { viewModel?.makeApiCall()}
    }

    override fun onBackPressed() {
        Tools.showDialog(this)
    }
}