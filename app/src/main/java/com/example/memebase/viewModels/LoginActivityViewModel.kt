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
import com.example.memebase.utils.EmailValidator
import com.example.memebase.utils.EmailValidator.Companion.isValidEmail
import com.example.memebase.utils.Tools.Companion.removeWhiteSpaces
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
    private val regLogMut= MutableLiveData<String>()
    val regLog: LiveData<String> = regLogMut
    var error: String? = ""
    private val context by lazy { app.applicationContext }


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

    init {
        regLogMut.value = ""
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

    fun checkRegister(){
        mutableSubmitted.value = true
       if (regLogMut.value.equals("REGISTER") || regLogMut.value.equals("")){
           login()
       }else if (regLogMut.value.equals("LOGIN") ){
           error = ""
           if (!registerUser())   {
               mutableSubmitted.value = false
               Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
           }
       }
    }

    fun onRegLogClick(){
        if (regLogMut.value.equals("REGISTER") || regLogMut.value.equals("")){
            regLogMut.value = "LOGIN"

        }else if (regLogMut.value.equals("LOGIN")){
            regLogMut.value = "REGISTER"
        }

    }

    fun login(){
        error = ""
        if (!isValidEmail(removeWhiteSpaces(usrId)) || !validationPassword()){
            mutableSubmitted.value = false
            if (error.equals("", true)){
                error = "Please enter valid Email"
            }
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            return
        }else{
            mutableSubmitted.value = false
            mutableUsrFnd.value = sharedPreferences.getString(usrId, "")?.equals(usrId, ignoreCase = true) == true &&
                    sharedPreferences.getString(usrPwd, "")?.equals(usrPwd, ignoreCase = true)  == true
        }
    }

    fun registerUser(): Boolean{
        if (error.equals("", true)){
            error = "Please enter valid Email"
        }
        if (isValidEmail(removeWhiteSpaces(usrIdUp)) && validationPasswordUp() && validationName()){
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