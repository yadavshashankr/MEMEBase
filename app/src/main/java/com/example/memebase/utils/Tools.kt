package com.example.memebase.utils

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.text.Editable
import androidx.core.app.ActivityCompat
import android.provider.MediaStore
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.memebase.R
import android.app.Activity
import android.content.Intent
import com.example.memebase.activities.LoginActivity


class Tools {



    companion object{

        fun removeWhiteSpaces(pString: String?): String{

            return pString!!.replace("\\s".toRegex(),"")

        }

        fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


        fun showDialog(context: Context) {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_layout)
            val body = dialog.findViewById(R.id.tvTitle) as TextView
            val yesBtn = dialog.findViewById(R.id.btn_yes) as Button
            val noBtn = dialog.findViewById(R.id.btn_no) as Button
            yesBtn.setOnClickListener {
              dialog.dismiss()
                (context as Activity).finishAffinity()
                context.startActivity(Intent(context, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
            noBtn.setOnClickListener { dialog.dismiss()}
            dialog.show()



        }
    }

}