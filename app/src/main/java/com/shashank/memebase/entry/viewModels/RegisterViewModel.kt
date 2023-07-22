package com.shashank.memebase.entry.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shashank.memebase.entry.data.UserPreferences
import com.shashank.memebase.entry.domain.EmailPatternValidator
import com.shashank.memebase.entry.domain.usecases.NameValidation
import com.shashank.memebase.entry.domain.usecases.PasswordPatternValidation
import com.shashank.memebase.entry.models.RegisterRequest
import com.shashank.memebase.usecases.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel  @Inject constructor(
    private val emailValidator: EmailPatternValidator,
    private val passwordPatternValidation: PasswordPatternValidation,
    private val nameValidation: NameValidation,
    networkStatus: LiveData<NetworkStatus>,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val mutableEmail = MutableLiveData(false)
    val email : LiveData<Boolean> = mutableEmail

    private val mutablePassword = MutableLiveData(false)
    val password : LiveData<Boolean> = mutablePassword

    private val mutableName = MutableLiveData(false)
    val name : LiveData<Boolean> = mutableName

    private val mutableFieldsValid = MutableLiveData(false)
    val validateFields : LiveData<Boolean> = mutableFieldsValid


    val networkObserver : LiveData<NetworkStatus> = networkStatus

    private val mutableRegistration = MutableLiveData(false)
    val registrationObserver : LiveData<Boolean> = mutableRegistration


//    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
//        throwable.printStackTrace()
//    }

    fun onEmailChange(email : String) {
        mutableEmail.value = emailValidator.isValidEmailPattern(email)
    }
    fun onPasswordChange(password : String) {
        mutablePassword.value = passwordPatternValidation.isPasswordPatternValid(password)
    }
    fun onNameChange(name : String) {
        mutableName.value = nameValidation.isValidName(name)
    }
    fun areFieldsValid() {
        val isNetworkAvailable = networkObserver.value == NetworkStatus.Available
        val areFieldsValidated = mutableEmail.value == true && mutablePassword.value == true && mutableName.value == true
        mutableFieldsValid.value = areFieldsValidated && isNetworkAvailable
    }
    fun register(registerModel: RegisterRequest){

        mutableRegistration.postValue( userPreferences.saveUser(registerModel))

//        viewModelScope.launch (Dispatchers.IO + coroutineExceptionHandler){
//            mutableRegistration.postValue(entryRepository.doRegistration(registerModel))
//        }
    }
}