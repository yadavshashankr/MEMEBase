package com.shashank.memebase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shashank.memebase.databinding.RecyclerListRowBinding
import com.shashank.memebase.models.memesModels.Memes
import com.shashank.memebase.utils.Tools.Companion.setViewGone
import com.shashank.memebase.utils.Tools.Companion.setViewVisible
import com.shashank.memebase.viewModels.MainActivityViewModel


class MemeRecyclerAdapter: RecyclerView.Adapter<MemeRecyclerAdapter.MyViewHolder>() {
    lateinit var viewModel: MainActivityViewModel
    var memeListData: ArrayList<Memes>? = ArrayList<Memes>()
    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeRecyclerAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
       val recyclerListRowBinding :
               RecyclerListRowBinding = RecyclerListRowBinding.inflate(inflater, parent, false)
        return MyViewHolder(recyclerListRowBinding)
    }

    override fun onBindViewHolder(holder: MemeRecyclerAdapter.MyViewHolder, position: Int) {
        memeListData?.get(position)
        holder.applicationBinding.memes = memeListData?.get(position)
        holder.applicationBinding.executePendingBindings()
        (holder).bind(memeListData?.get(position) as Memes)
    }

    override fun getItemCount(): Int {
        return memeListData?.size!!
    }

    inner class MyViewHolder(var applicationBinding: RecyclerListRowBinding) : RecyclerView.ViewHolder(applicationBinding.root) {
        fun bind(memes: Memes) {
            applicationBinding.llHead.setOnClickListener {
                if (applicationBinding.ivSetImg.visibility == View.VISIBLE){
                    setViewGone(applicationBinding.ivSetImg)
                }else{
                    setViewVisible(applicationBinding.ivSetImg)
                }
            }

            applicationBinding.ivDownload.setOnClickListener {
                    setViewVisible(applicationBinding.ivSetImg)
                    downloadMeme(memes)

            }
        }
    }

    private fun downloadMeme(memes: Memes) {

        viewModel.downloadMeme(context, memes)

        val memeName = memes.name.replace(" ", "_") + ".jpeg"
        Toast.makeText(context, "Saved as Pictures/MEMEBase/$memeName", Toast.LENGTH_LONG).show()
    }
}