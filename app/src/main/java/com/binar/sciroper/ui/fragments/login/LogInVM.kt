package com.binar.sciroper.ui.fragments.login

import androidx.lifecycle.*
import com.binar.sciroper.data.db.user.AuthDetails
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.data.retrofit.AuthResponse
import com.binar.sciroper.data.retrofit.Retrofit
import com.binar.sciroper.util.BaseViewModel
import com.binar.sciroper.util.UiState
import kotlinx.coroutines.launch
import java.lang.Exception

class LogInVM(
    private val userDao: UserDAO,
) : BaseViewModel<UiState>() {

    private val sharedPreference: AppSharedPreference = AppSharedPreference
    val inputEmail = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()
    private var _errorToast = false
    val errorToast get() = _errorToast
    private val _onSuccess = MutableLiveData<AuthResponse>()
    val onSuccess: LiveData<AuthResponse> get() = _onSuccess
    private val _onError = MutableLiveData<String>()
    val onError: LiveData<String> = _onError


    fun logIn() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val loginResponse = Retrofit.retrofitService.login(
                    AuthDetails(
                        email = inputEmail.value!!,
                        password = inputPassword.value!!,
                        username = ""
                    )
                )
                sharedPreference.userToken = loginResponse.data.token
                sharedPreference.idBinar = loginResponse.data._id
                uiState.value = UiState.Success(loginResponse)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState.value = UiState.Error("${e.message}")
            }
        }
    }
}

class LogInVMFactory(
    private val userDao: UserDAO
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogInVM::class.java)) {
            return LogInVM(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}