package com.shashank.memebase.utils

import android.content.Context
import android.view.View
import android.content.SharedPreferences
import android.widget.Toast


class Tools  {
    companion object{
        var usrId: String = ""
        var usrPwd: String = ""
        var usrNm: String = ""
        var usrIdUp: String = ""
        var usrPswUp: String = ""
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

    }
}