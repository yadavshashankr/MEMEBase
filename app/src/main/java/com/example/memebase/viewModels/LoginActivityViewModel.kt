package com.example.memebase.viewModels

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.memebase.utils.Tools
import com.example.memebase.utils.Tools.Companion.getSigInValidation
import com.example.memebase.utils.Tools.Companion.getSigUpValidation
import com.example.memebase.utils.Tools.Companion.isUserPresent
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

    var mutableSignInValidation=MutableLiveData<Boolean>()
    var liveSignInValidation:LiveData<Boolean> = mutableSignInValidation

    val regLogMutable= MutableLiveData<String>()
    val regLog: LiveData<String> = regLogMutable

    private val context by lazy { app.applicationContext }

    init {
        regLogMutable.value = "REGISTER"
    }

    fun checkRegister(){

       if (regLogMutable.value.equals("REGISTER") || regLogMutable.value.equals("")){
           login()
       }else if (regLogMutable.value.equals("LOGIN") ){
           if (!registerUser()) {
               Tools.shortToast(context, Tools.error)
           }
       }
    }
    fun onRegLogClick(){
        if (regLogMutable.value.equals("REGISTER") || regLogMutable.value.equals("")){
            regLogMutable.value = "LOGIN"

        }else if (regLogMutable.value.equals("LOGIN")){
            regLogMutable.value = "REGISTER"
        }

    }
    fun login(){
        if (getSigInValidation()){
            mutableSignInValidation.postValue(true)
            mutableUsrFnd.value = siginInSuccess(sharedPreferences)
        }else{
            mutableSignInValidation.postValue(false)
            mutableUsrFnd.value = false
        }
    }
    fun registerUser(): Boolean{
        if (getSigUpValidation()){
            putRegisterCredsToConsistentStorage(sharedPreferences)
            mutableUsrReg.value = true
            return true
        }else{
            mutableUsrReg.value = false
        }
        return mutableUsrReg.value == true
    }

    fun isUserPresent() : Boolean{
        return isUserPresent(sharedPreferences)
    }
}