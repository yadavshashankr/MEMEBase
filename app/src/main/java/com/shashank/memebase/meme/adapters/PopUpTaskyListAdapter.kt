package com.shashank.memebase.agenda.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.shashank.memebase.databinding.LayoutPopUpTaskyItemBinding

@SuppressLint("ViewHolder")
class PopUpTaskyListAdapter(private var context: Context, private var values : ArrayList<String>): ArrayAdapter<String>(context, 0, values){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutPopUpTaskyItemBinding: LayoutPopUpTaskyItemBinding = LayoutPopUpTaskyItemBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        val rowView = layoutPopUpTaskyItemBinding.root

        layoutPopUpTaskyItemBinding.label = values[position]

        layoutPopUpTaskyItemBinding.vwDivider.isVisible = position != (values.size - 1)

        return rowView
    }
}