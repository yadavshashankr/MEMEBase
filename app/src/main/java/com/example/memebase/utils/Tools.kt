package com.example.memebase.utils

import android.content.Context
import android.content.pm.PackageManager
import android.text.Editable
import androidx.core.app.ActivityCompat

class Tools {



    companion object{

        fun removeWhiteSpaces(pString: String?): String{

            return pString!!.replace("\\s".toRegex(),"")

        }

        fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


    }

}