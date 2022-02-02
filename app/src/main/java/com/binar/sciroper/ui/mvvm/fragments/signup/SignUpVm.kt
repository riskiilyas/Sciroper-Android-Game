package com.binar.sciroper.ui.mvvm.fragments.signup

import android.util.Patterns
import androidx.lifecycle.*
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference
import kotlinx.coroutines.*

class SignUpVm(private val userDao: UserDAO) : ViewModel() {
    private val uiScope = CoroutineScope(Dispatchers.Main + Job())
    val inputUsername = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()
    val inputRePassword = MutableLiveData<String>()
    private var selectedAvatarId: Int = R.drawable.avatar21
    private val _navToMenu = MutableLiveData<Boolean>()
    val navToMenu: LiveData<Boolean> get() = _navToMenu
    private val _errorToast = MutableLiveData<Boolean>()
    val errorToast: LiveData<Boolean> get() = _errorToast
    private val _errorEmailFormatToast = MutableLiveData<Boolean>()
    val errorEmailFormatToast: LiveData<Boolean> get() = _errorEmailFormatToast
    private val _nonMatchingPassword = MutableLiveData<Boolean>()
    val nonMatchingPassword: LiveData<Boolean> get() = _nonMatchingPassword
    private val sharedPreference = AppSharedPreference


    fun signUp() {
        uiScope.launch {
            val getUserCredentials = userDao.getUserByUsernameAndEmail(
                username = inputUsername.value!!,
                email = inputEmail.value!!
            )

            if (getUserCredentials == null) {
                if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value.toString())
                        .matches()
                ) {
                    _errorEmailFormatToast.value = true
                } else if (inputPassword.value != inputRePassword.value) {
                    _nonMatchingPassword.value = true
                } else {
                    insertUser(
                        User(
                            username = inputUsername.value!!,
                            email = inputEmail.value!!,
                            password = inputPassword.value!!,
                            avatarId = selectedAvatarId
                        )
                    )
                    setSharedPreference()
                    resetInputFields()
                    _navToMenu.value = true
                }
            } else {
                _errorToast.value = true
            }
        }
    }

    private fun insertUser(user: User) {
        viewModelScope.launch { userDao.insertUser(user) }
    }

    private fun resetInputFields() {
        inputUsername.value = ""
        inputEmail.value = ""
        inputPassword.value = ""
        inputRePassword.value = ""
    }

    fun setAvatarId(avatarId: Int) {
        selectedAvatarId = avatarId
    }

    fun setEndNav() {
        _navToMenu.value = false
    }

    fun setEndToast() {
        _errorToast.value = false
    }

    fun setEndErrorEmailFormatToast() {
        _errorEmailFormatToast.value = false
    }

    fun setNonMatchingPasswordToast() {
        _nonMatchingPassword.value = true
    }

    private suspend fun getUser(username: String): User = uiScope.async {
        userDao.getUserByUserName(inputUsername.value!!)
    }.await()

    private suspend fun setSharedPreference() {
        sharedPreference.id = getUser(inputUsername.value!!).id
        sharedPreference.isLogin = true
    }
}

class SignUpVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpVm::class.java)) {
            return SignUpVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}