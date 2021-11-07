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
import androidx.lifecycle.lifecycleScope
import com.example.memebase.models.memesModels.MemeModel
import com.likethesalad.android.aaper.api.EnsurePermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var viewModel: MainActivityViewModel? = null
    private var memeRecyclerAdapter: MemeRecyclerAdapter? = null


    private var ivNoInternet: ImageView? = null
    private var recyclerView: RecyclerView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()



        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel?.getMemeListObserver()?.observe(this, {
            if (it != null) {
                ivNoInternet?.visibility = View.GONE
                recyclerView?.visibility = View.VISIBLE
                memeRecyclerAdapter?.memeListData = it.data.memes
                memeRecyclerAdapter?.notifyDataSetChanged()
            } else {

                ivNoInternet?.visibility = View.VISIBLE
                recyclerView?.visibility = View.GONE
            }
        })

        loadAPIData()


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {

                videoComp()

            }


    }
    @EnsurePermissions(permissions = [Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE])
    private fun videoComp() {
        startActivity(Intent(this, SelectVideoActivity::class.java))
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


    }






    private fun initRecyclerView() {

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        ivNoInternet = findViewById<ImageView>(R.id.iv_noInternet)
        recyclerView.apply {

         this?.layoutManager = LinearLayoutManager(this@MainActivity)

         val decoration = DividerItemDecoration(applicationContext,
             DividerItemDecoration.VERTICAL)
            this?.addItemDecoration(decoration)

         memeRecyclerAdapter= MemeRecyclerAdapter()
         this?.adapter = memeRecyclerAdapter

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadAPIData(){

        Executors.newSingleThreadExecutor().execute { viewModel?.makeApiCall()}

//        AsyncTask.execute(()->viewModel?.makeApiCall())
//        GlobalScope.launch { viewModel?.makeApiCall() }


    }






}