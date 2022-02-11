package com.binar.sciroper.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LogInVM(
    private val userDao: UserDAO,
) : ViewModel() {

    private val uiScope = CoroutineScope(Dispatchers.Main + Job())
    private val sharedPreference: AppSharedPreference = AppSharedPreference
    val inputEmail = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()
    private val _errorToast = MutableLiveData<Boolean>()
    val errorToast: LiveData<Boolean> get() = _errorToast
    private val _navToMenu = MutableLiveData<Boolean>()
    val navToMenu: LiveData<Boolean> get() = _navToMenu

    fun logIn() {
        if (inputEmail.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                val userCredential =
                    userDao.getUserByEmailAndPassword(
                        email = inputEmail.value!!,
                        password = inputPassword.value!!
                    )
                if (userCredential != null) {
                    sharedPreference.id = userCredential.id
                    sharedPreference.isLogin = true
                    inputEmail.value = ""
                    inputPassword.value = ""
                    _navToMenu.value = true
                } else {
                    _errorToast.value = true
                }
            }
        }
    }

    fun setEndNav() {
        _navToMenu.value = false
    }

    fun setEndToast() {
        _errorToast.value = false
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