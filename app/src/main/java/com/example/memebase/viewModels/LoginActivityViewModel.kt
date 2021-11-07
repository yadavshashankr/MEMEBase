package com.example.memebase.viewModels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.*

import com.example.memebase.activities.LoginActivity
import com.example.memebase.activities.MainActivity
import com.example.memebase.db.AppDatabase
import com.example.memebase.db.Dao
import com.example.memebase.globals.ApplicationConstant
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(private val sharedPreferences: SharedPreferences, app: Application):
    AndroidViewModel(app) {

    var mutableUsrFnd=MutableLiveData<Boolean>()
    var liveUsrFnd:LiveData<Boolean> = mutableUsrFnd

    var mutableUsrReg=MutableLiveData<Boolean>()
    var liveUsrReg:LiveData<Boolean> = mutableUsrReg

    var mutableSubmitted=MutableLiveData<Boolean>()
    var liveSubmitted:LiveData<Boolean> = mutableSubmitted


    var usrId: String = ""
    var usrPwd: String = ""
    var usrNm: String = ""
    var usrIdUp: String = ""
    var usrPswUp: String = ""
    var usrNmFld: String = "REGISTER"
    private val regLogMut= MutableLiveData<String>()
    val regLog: LiveData<String> = regLogMut
    var error: String? = ""
    private val context by lazy { app.applicationContext }


    private val EMAIL_ADDRESS: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun afterUserIdChange(s: CharSequence) {
        //Log.i("truc", s.toString());
        this.usrId = s.toString()
    }

    fun afterPasswordChange(s: CharSequence) {
        //Log.i("truc", s.toString());
        this.usrPwd = s.toString()
    }

    fun afterUserNameChange(s: CharSequence) {
        //Log.i("truc", s.toString());
        this.usrNm = s.toString()
    }

    fun afterUserIdUpChange(s: CharSequence) {
        //Log.i("truc", s.toString());
        this.usrIdUp = s.toString()
    }

    fun afterUserPasswordUpChange(s: CharSequence) {
        //Log.i("truc", s.toString());
        this.usrPswUp = s.toString()
    }

    init {

        regLogMut.value = ""
    }

    fun validationEmail(): Boolean{

        error = "Please enter Proper Email address"
        return EMAIL_ADDRESS.matcher(usrId).matches()
    }

    fun validationPassword(): Boolean{
        error = "Please enter Proper Password"
        return !usrPwd.isEmpty()
    }

    fun validationEmailUp(): Boolean{
        error = "Please enter Proper Email address"
        return EMAIL_ADDRESS.matcher(usrIdUp).matches()
    }

    fun validationPasswordUp(): Boolean{
        error = "Please enter Proper Password"
        return !usrPswUp.isEmpty()
    }

    fun validationName(): Boolean{
        error = "Please enter Proper Username"
        return !usrNm.isEmpty()
    }

    fun checkRegister(){
        mutableSubmitted.value = true
       if (regLogMut.value.equals("REGISTER") || regLogMut.value.equals("")){

           login()

       }else if (regLogMut.value.equals("LOGIN") ){
           if (!registerUser())   {
               mutableSubmitted.value = false
               Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
           }

       }
    }

    fun onRegLogClick(){
        if (regLogMut.value.equals("REGISTER") || regLogMut.value.equals("")){
            usrNmFld = "LOGIN"
            regLogMut.value = "LOGIN"

        }else if (regLogMut.value.equals("LOGIN")){
            usrNmFld = "REGISTER"
            regLogMut.value = "REGISTER"

        }

    }




    fun login(){
        if (!validationEmail() || !validationPassword()){
            mutableSubmitted.value = false
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            return
        }else{
            mutableSubmitted.value = false
            mutableUsrFnd.value = sharedPreferences.getString(usrId, "")?.equals(usrId, ignoreCase = true) == true &&
                    sharedPreferences.getString(usrPwd, "")?.equals(usrPwd, ignoreCase = true)  == true


        }


    }




    fun registerUser(): Boolean{
        if (validationEmailUp() && validationPasswordUp() && validationName()){

            sharedPreferences.edit().putString(usrIdUp, usrIdUp).apply()
            sharedPreferences.edit().putString(usrPswUp, usrPswUp).apply()
            sharedPreferences.edit().putString(usrNm, usrNm).apply()
            mutableUsrReg.value = true
            mutableSubmitted.value = false
            return true
        }else{
            mutableUsrReg.value = false
        }
        return false
    }

   

}