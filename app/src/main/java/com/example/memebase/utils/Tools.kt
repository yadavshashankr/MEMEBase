package com.example.memebase.utils

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.view.Window
import android.widget.Button
import com.example.memebase.R
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.example.memebase.ui.LoginActivity
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class Tools  {
    companion object{
        var usrId: String = ""
        var usrPwd: String = ""
        var usrNm: String = ""
        var usrIdUp: String = ""
        var usrPswUp: String = ""
        var error: String = ""

        fun removeWhiteSpaces(pString: String?): String{
            return pString!!.replace("\\s".toRegex(),"")
        }
        fun isSpaceInString(string: String?): Boolean{
            if (string?.contains(" ") == true){
                return false
            }
            return true
        }
        fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

        fun showDialog(context: Context) {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_layout)
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

        fun setViewVisible(view: View?){
            view?.visibility = View.VISIBLE
        }
        fun setViewInVisible(view: View?){
            view?.visibility = View.INVISIBLE
        }
        fun setViewGone(view: View?){
            view?.visibility = View.GONE
        }
        fun rotateDown(imageView: ImageView){
            val mCurrRotation = 0f
            val fromRotation: Float = mCurrRotation
            val toRotation = 180f

            val rotateAnim = RotateAnimation(
                fromRotation, toRotation, imageView.width / 2f, imageView.height / 2f
            )
            rotateAnim.duration = 300
            rotateAnim.fillAfter = true
            imageView.startAnimation(rotateAnim)
        }

        fun rotateUp(imageView: ImageView){
            val mCurrRotation = 180f
            val fromRotation: Float = mCurrRotation
            val toRotation = 360f

            val rotateAnim = RotateAnimation(
                fromRotation, toRotation, imageView.width / 2f, imageView.height / 2f
            )
            rotateAnim.duration = 300
            rotateAnim.fillAfter = true
            imageView.startAnimation(rotateAnim)
        }

        fun afterUserIdChange(s: CharSequence) {
            this.usrId = s.toString()
        }

        fun afterPasswordChange(s: CharSequence) {
            this.usrPwd = s.toString()
        }

        fun afterUserNameChange(s: CharSequence) {
            this.usrNm = s.toString()
        }

        fun afterUserIdUpChange(s: CharSequence) {
            this.usrIdUp = s.toString()
        }

        fun afterUserPasswordUpChange(s: CharSequence) {
            this.usrPswUp = s.toString()
        }

        private fun validationPassword(): Boolean{
            error = "Please Enter Password"
            return !usrPwd.isEmpty()
        }

        private fun validationPasswordUp(): Boolean{
            error = "Please Enter Password"
            return !usrPswUp.isEmpty()
        }

        private fun validationName(): Boolean{
            error = "Please Enter Username"
            return !usrNm.isEmpty()
        }

       private fun getEmailLoginVerified(): Boolean{
           return getEmailVerificationProcess(usrId)
       }
        private fun getEmailVerificationProcess(usrId: String?): Boolean{
            error = "Please Enter Valid Email"
            return EmailValidator.isValidEmail(usrId)
        }

        fun getSigInValidation(): Boolean {
            return when (getEmailLoginVerified()){
                true -> validationPassword()
                false -> false
            }
        }
        private fun getEmailSignUpVerified(): Boolean{
            return getEmailUpVerificationProcess(usrIdUp)
        }

        fun getSigUpValidation(): Boolean {
            return when(getEmailSignUpVerified()){
                true -> if (validationPasswordUp()) validationName() else false
                false -> false
            }
        }
        private fun getEmailUpVerificationProcess(usrId: String?): Boolean{
            error = "Please Enter Valid Email"
            return EmailValidator.isValidEmail(usrId)
        }

        fun putRegisterCredsToConsistentStorage( sp: SharedPreferences){
            sp.edit().putString("userid", usrIdUp).apply()
            sp.edit().putString("password", usrPswUp).apply()
            sp.edit().putString("name", usrNm).apply()
        }

        fun siginInSuccess(sp: SharedPreferences): Boolean{
            error = "User Not Found"
            return  sp.getString("userid", "")?.equals(usrId, ignoreCase = true) == true &&
                    sp.getString("password", "")?.equals(usrPwd, ignoreCase = true)  == true
        }

        fun isUserPresent(sp: SharedPreferences): Boolean{
            return sp.getString("userid", "")?.equals("") == false
        }

        fun shortToast(context: Context, message: String?){
            Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
        }

        fun longToast(context: Context, message: String?){
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }

        fun saveImageToStorage(context: Context, bitmapObject : Bitmap, name : String) {
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
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .toString()
                val image = File(imagePath, "memes")
                FileOutputStream(image)
            }
            imageOutStream.use { imageOutStream ->
                bitmapObject.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)
            }
        }
    }
}