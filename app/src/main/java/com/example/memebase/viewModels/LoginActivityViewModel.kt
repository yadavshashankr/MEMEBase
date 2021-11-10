package com.example.memebase.viewModels

import android.app.Application
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.*
import com.example.memebase.utils.Tools
import com.example.memebase.utils.Tools.Companion.getSigInValidation
import com.example.memebase.utils.Tools.Companion.getSigUpValidation
import com.example.memebase.utils.Tools.Companion.putRegisterCredsToConsistentStorage
import com.example.memebase.utils.Tools.Companion.siginInSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(private val sharedPreferences: SharedPreferences, app: Application):
    AndroidViewModel(app)
{

    var mutableUsrFnd=MutableLiveData<Boolean>()
    var liveUsrFnd:LiveData<Boolean> = mutableUsrFnd

    var mutableUsrReg=MutableLiveData<Boolean>()
    var liveUsrReg:LiveData<Boolean> = mutableUsrReg

    var mutableSubmitted=MutableLiveData<Boolean>()
    var liveSubmitted:LiveData<Boolean> = mutableSubmitted

    var mutableSignUpVlidation=MutableLiveData<Boolean>()
    var liveSignUpValidation:LiveData<Boolean> = mutableSignUpVlidation

    var mutableSignInVlidation=MutableLiveData<Boolean>()
    var liveSignInValidation:LiveData<Boolean> = mutableSignInVlidation

    private val regLogMut= MutableLiveData<String>()
    val regLog: LiveData<String> = regLogMut

    private val context by lazy { app.applicationContext }

    init {
        regLogMut.value = ""
    }

    fun checkRegister(){
        mutableSubmitted.value = true
       if (regLogMut.value.equals("REGISTER") || regLogMut.value.equals("")){
           login()
       }else if (regLogMut.value.equals("LOGIN") ){
           if (!registerUser()) {
               mutableSubmitted.value = false
               Toast.makeText(context, Tools.error, Toast.LENGTH_SHORT).show()
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
        if (getSigInValidation()){
            mutableSignInVlidation.postValue(true)
            mutableSubmitted.value = true
            mutableUsrFnd.value = siginInSuccess(sharedPreferences)
        }else{
            mutableSignInVlidation.postValue(false)
            mutableSubmitted.value = false
            mutableUsrFnd.value = false
        }
    }
    fun registerUser(): Boolean{
        if (getSigUpValidation()){
            mutableSignUpVlidation.postValue(true)
            putRegisterCredsToConsistentStorage(sharedPreferences)
            mutableUsrReg.value = true
            mutableSubmitted.value = false
            return true
        }else{
            mutableUsrReg.value = false
            mutableSignUpVlidation.postValue(false)
        }
        return false
    }
}