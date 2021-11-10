package com.example.memebase.utils

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.memebase.R
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.example.memebase.ui.LoginActivity
import javax.inject.Inject
import android.content.SharedPreferences
import android.widget.Toast


class Tools @Inject constructor(sharedPreference: SharedPreferences) {




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
            var mCurrRotation = 0f
            val fromRotation: Float = mCurrRotation
            var toRotation = 180f

            val rotateAnim = RotateAnimation(
                fromRotation, toRotation, imageView.width / 2f, imageView.height / 2f
            )
            rotateAnim.duration = 300
            rotateAnim.fillAfter = true
            imageView.startAnimation(rotateAnim)
        }

        fun rotateUp(imageView: ImageView){
            var mCurrRotation = 180f
            val fromRotation: Float = mCurrRotation
            var toRotation = 360f

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

        fun validationPassword(): Boolean{
            error = "Please Enter Password"
            return !usrPwd.isEmpty()
        }

        fun validationPasswordUp(): Boolean{
            error = "Please Enter Password"
            return !usrPswUp.isEmpty()
        }

        fun validationName(): Boolean{
            error = "Please Enter Username"
            return !usrNm.isEmpty()
        }

       fun getEmailLoginVerified(): Boolean{
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
        fun getEmailSignUpVerified(): Boolean{
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
            sp.edit().putString(usrIdUp, usrIdUp).apply()
            sp.edit().putString(usrPswUp, usrPswUp).apply()
            sp.edit().putString(usrNm, usrNm).apply()
        }

        fun siginInSuccess(sp: SharedPreferences): Boolean{
            error = "User Not Found"
            return  sp.getString(usrId, "")?.equals(usrId, ignoreCase = true) == true &&
                    sp.getString(usrPwd, "")?.equals(usrPwd, ignoreCase = true)  == true
        }

        fun shortToast(context: Context, message: String?){
            Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
        }

        fun longToast(context: Context, message: String?){
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }
}