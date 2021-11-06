package com.example.memebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.memebase.R
import com.example.memebase.models.memesModels.Memes



class MemeRecyclerAdapter: RecyclerView.Adapter<MemeRecyclerAdapter.MyViewHolder>() {

    var memeListData: ArrayList<Memes>? = ArrayList<Memes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeRecyclerAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_row, parent, false )
        return MyViewHolder(inflater)

    }

    override fun onBindViewHolder(holder: MemeRecyclerAdapter.MyViewHolder, position: Int) {
        memeListData?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return memeListData?.size!!
    }

    class  MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val tvName = view.findViewById<TextView>(R.id.tvName)
        private val tvWidth = view.findViewById<TextView>(R.id.tvWidth)
        private val tvHeight = view.findViewById<TextView>(R.id.tvHeight)
        private val tvBoxCount = view.findViewById<TextView>(R.id.tvBoxCount)
        private val thumbImageView = view.findViewById<ImageView>(R.id.thumbImageView)

        fun bind(model : Memes){
            tvName.text = model.name
            tvWidth.text = model.width.toString()
            tvHeight.text =  model.height.toString()
            tvBoxCount.text =  model.box_count.toString()

            val url  =  model.url

//            tvName.text = model.data.memes?.get(absoluteAdapterPosition)?.name
//            tvWidth.text = model.data.memes?.get(absoluteAdapterPosition)?.width.toString()
//            tvHeight.text =  model.data.memes?.get(absoluteAdapterPosition)?.height.toString()
//            tvBoxCount.text =  model.data.memes?.get(absoluteAdapterPosition)?.box_count.toString()
//
//            val url  =  model.data.memes?.get(absoluteAdapterPosition)?.url
            Glide.with(thumbImageView)
                .load(url)
                .circleCrop()
                .into(thumbImageView)

        }
    }
}