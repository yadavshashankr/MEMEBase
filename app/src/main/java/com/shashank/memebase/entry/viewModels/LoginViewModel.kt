package com.shashank.memebase.entry.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shashank.memebase.entry.domain.EmailPatternValidator
import com.shashank.memebase.entry.domain.usecases.PasswordPatternValidation
import com.shashank.memebase.entry.models.AuthenticationRequest
import com.shashank.memebase.entry.data.UserPreferences
import com.shashank.memebase.usecases.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailValidator: EmailPatternValidator,
    private val passwordPatternValidation: PasswordPatternValidation,
    networkStatus: LiveData<NetworkStatus>,
    private val userPreferences: UserPreferences
): ViewModel() {

    private val mutableEmail = MutableLiveData(false)
    val email : LiveData<Boolean> = mutableEmail

    private val mutablePassword = MutableLiveData(false)
    val password : LiveData<Boolean> = mutablePassword

    private val mutableFieldsValid = MutableLiveData(false)
    val validateFields : LiveData<Boolean> = mutableFieldsValid

    val networkObserver : LiveData<NetworkStatus> = networkStatus

    private val mutableLogin = MutableLiveData<Boolean>()
    val loginObserver : LiveData<Boolean> = mutableLogin



    fun onEmailChange(email : String) {
        mutableEmail.value = emailValidator.isValidEmailPattern(email)
    }
    fun onPasswordChange(password : String) {
        mutablePassword.value = passwordPatternValidation.isPasswordPatternValid(password)
    }
    fun areFieldsValid() {
        val isNetworkAvailable = networkObserver.value == NetworkStatus.Available
        val areFieldsValidated = mutableEmail.value == true && mutablePassword.value == true
        mutableFieldsValid.value = areFieldsValidated  && isNetworkAvailable
    }

    fun login(authenticationModel: AuthenticationRequest){

        mutableLogin.postValue(userPreferences.verifyUserCredentials(authenticationModel.email, authenticationModel.password))
    }
}