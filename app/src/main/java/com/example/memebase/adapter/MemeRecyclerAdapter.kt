package com.example.memebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memebase.R
import com.example.memebase.databinding.RecyclerListRowBinding
import com.example.memebase.models.memesModels.Memes
import com.example.memebase.utils.Tools.Companion.loadInGlide
import com.example.memebase.utils.Tools.Companion.loadInGlideCir
import com.example.memebase.utils.Tools.Companion.rotateDown
import com.example.memebase.utils.Tools.Companion.rotateUp
import com.example.memebase.utils.Tools.Companion.setViewGone
import com.example.memebase.utils.Tools.Companion.setViewVisible


class MemeRecyclerAdapter: RecyclerView.Adapter<MemeRecyclerAdapter.MyViewHolder>() {
    var memeListData: ArrayList<Memes>? = ArrayList<Memes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeRecyclerAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
       var recyclerListRowBinding :
               RecyclerListRowBinding = RecyclerListRowBinding.inflate(inflater, parent, false)
        return MyViewHolder(recyclerListRowBinding)
    }

    override fun onBindViewHolder(holder: MemeRecyclerAdapter.MyViewHolder, position: Int) {
        memeListData?.get(position)
        holder.applicationBinding.memes = memeListData?.get(position)
        holder.applicationBinding.executePendingBindings()
        (holder as MemeRecyclerAdapter.MyViewHolder).bind()

    }

    override fun getItemCount(): Int {
        return memeListData?.size!!
    }




    inner class MyViewHolder(var applicationBinding: RecyclerListRowBinding) : RecyclerView.ViewHolder(applicationBinding.root) {

        fun bind() {
            applicationBinding.ivArrow.setOnClickListener {

                if (applicationBinding.ivSetImg.visibility == View.VISIBLE){
                    setViewGone(applicationBinding.ivSetImg)
                    rotateUp(applicationBinding.ivArrow)
                }else{
                    setViewVisible(applicationBinding.ivSetImg)
                    rotateDown(applicationBinding.ivArrow)
                }


            }

        }


    }

}