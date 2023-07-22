package com.shashank.memebase.storage.domain

import android.graphics.Bitmap
import android.net.Uri

interface SaveData {

    fun saveImageToStorage(bitmapObject : Bitmap, name : String)

    fun getFileName(uri: Uri) : String
}