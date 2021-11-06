package com.example.memebase.utils

class Tools {

    companion object{

        fun removeWhiteSpaces(pString: String?): String{

            return pString!!.replace("\\s".toRegex(),"")

        }
    }

}