package com.shashank.memebase.utils

import android.content.Context
import android.view.View
import android.widget.Toast

class Tools  {
    companion object{
        var error: String = ""

        fun isSpaceInString(string: String?): Boolean{
            if (string?.contains(" ") == true){
                return false
            }
            return true
        }
        fun setViewVisible(view: View?){
            view?.visibility = View.VISIBLE
        }
        fun setViewGone(view: View?){
            view?.visibility = View.GONE
        }
        fun longToast(context: Context, message: String?){
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }

    }
}