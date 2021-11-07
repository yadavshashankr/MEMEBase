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
import android.view.animation.RotateAnimation



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
        private val ivArrow = view.findViewById<ImageView>(R.id.iv_arrow)
        private val ivImageSet = view.findViewById<ImageView>(R.id.iv_setImg)
        private var mCurrRotation = 0
        fun bind(model : Memes){
            tvName.text = model.name
            tvWidth.text = model.width.toString()
            tvHeight.text =  model.height.toString()
            tvBoxCount.text =  model.box_count.toString()

            val url  =  model.url

            loadInGlideCir(thumbImageView, url)
            loadInGlide(ivImageSet, url)


            ivArrow.setOnClickListener {

                if (ivImageSet.visibility == View.VISIBLE){
                    ivImageSet.visibility = View.GONE
                    rotate(ivArrow)

                }else{
                    ivImageSet.visibility = View.VISIBLE
                    rotate(ivArrow)
                }


            }

        }
        fun rotate(imageView: ImageView){
            val fromRotation = mCurrRotation.toFloat()
            val toRotation = 180.let { mCurrRotation += it; mCurrRotation }.toFloat()
            val rotateAnim = RotateAnimation(
                fromRotation, toRotation, imageView.width / 2f, imageView.height / 2f
            )
            rotateAnim.duration = 300
            rotateAnim.fillAfter = true
            imageView.startAnimation(rotateAnim)
        }

        fun loadInGlideCir(imageView: ImageView, url: String?){
            Glide.with(imageView)
                .load(url)
                .circleCrop()
                .into(imageView)
        }
        fun loadInGlide(imageView: ImageView, url: String?){
            Glide.with(imageView)
                .load(url)
                .into(imageView)
        }
    }

}