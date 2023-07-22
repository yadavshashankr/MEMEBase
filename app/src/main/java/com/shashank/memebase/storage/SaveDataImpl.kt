package com.shashank.memebase.storage

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import com.shashank.memebase.storage.domain.SaveData
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject


open class SaveDataImpl @Inject constructor(val context: Context) : SaveData{

    @SuppressLint("ObsoleteSdkInt")
    override fun saveImageToStorage(bitmapObject : Bitmap, name : String) {
        val imageOutStream: OutputStream? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "$name.jpeg")
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/MEMEBase")
            val uri =
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            context.contentResolver.openOutputStream(uri as Uri)
        } else {
            val imagePath =
                Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES + "/MEMEBase")
                    .toString()
            val image = File(imagePath, "memes")
            FileOutputStream(image)
        }
        imageOutStream.use { imageOutStream ->
            bitmapObject.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)
        }
    }

    @SuppressLint("Range")
    override fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor = context.contentResolver.query(uri, null, null, null, null) as Cursor
            try {
                if (cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }
}