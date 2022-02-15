package com.binar.sciroper.ui.fragments.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.*
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.AuthDetails
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.firebase.FirebaseRtdb
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.data.retrofit.AuthResponse
import com.binar.sciroper.data.retrofit.Retrofit
import com.binar.sciroper.util.App
import com.binar.sciroper.util.BaseViewModel
import com.binar.sciroper.util.UiState
import kotlinx.coroutines.launch
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*


class SignUpVm(private val userDao: UserDAO) : BaseViewModel<UiState>() {
    private val sharedPreferences = AppSharedPreference
    val inputUsername = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()
    val inputRePassword = MutableLiveData<String>()

    private val firebase = FirebaseRtdb()
    private val _usernameLength = MutableLiveData<Boolean>()
    val usernameLength: LiveData<Boolean> get() = _usernameLength
    private val _onPasswordError = MutableLiveData<Boolean>()
    val onPasswordError: LiveData<Boolean> get() = _onPasswordError
    private val _onInvalidEmail = MutableLiveData<Boolean>()
    val onInvalidEmail: LiveData<Boolean> get() = _onInvalidEmail
    private val _fieldChecker = MutableLiveData<Boolean>()
    val fieldChecker: LiveData<Boolean> get() = _fieldChecker
    private var selectedAvatarId: Int = R.drawable.avatar21


    fun signUp() {
        fieldChecker()
        if (fieldChecker.value!!) {
            uiState.value = UiState.Loading
            viewModelScope.launch {
                try {
                    val signUpResponse = Retrofit.retrofitService.register(
                        AuthDetails(
                            username = inputUsername.value!!,
                            email = inputEmail.value!!,
                            password = inputPassword.value!!
                        )
                    )
                    val logInResponse = Retrofit.retrofitService.login(
                        AuthDetails(
                            email = inputEmail.value!!,
                            password = inputPassword.value!!,
                            username = ""
                        )
                    )
                    sharedPreferences.userToken = logInResponse.data.token
                    sharedPreferences.idBinar = logInResponse.data._id
                    sharedPreferences.username = logInResponse.data.username
                    sharedPreferences.email = logInResponse.data.email
                    sharedPreferences.isLogin = true
                    firebase.addUser(
                        idBinar = logInResponse.data._id,
                        email = inputEmail.value!!,
                        username = inputUsername.value!!,
                        password = inputPassword.value!!,
                        avatarId = selectedAvatarId
                    )
                    uiState.value = UiState.Success(signUpResponse)
                    sharedPreferences.isLogin = true
                    loadUser()
                } catch (e: Exception) {
                    e.printStackTrace()
                    uiState.value = UiState.Error(e.message.toString())
                    Log.i("sign_up", "${e.message}")
                }
            }
        }
    }

    fun setAvatarId(avatarId: Int) {
        selectedAvatarId = avatarId
    }

    private fun loadUser() {
        val userDao = App.appDb.getUserDao()
        val database = FirebaseRtdb()
        val loadedUser = User()
        val target = database.database.getReference("Users").child(AppSharedPreference.idBinar!!)
        target.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                loadedUser.avatarId =
                    snapshot.child("avatarId").value.toString()
                        .toInt()
                loadedUser.level =
                    snapshot.child("level").value.toString()
                        .toInt()
                loadedUser.achievement =
                    snapshot.child("achievement").value.toString()
                loadedUser.coin =
                    snapshot.child("coin").value.toString().toInt()
                loadedUser.idBinar =
                    snapshot.child("idBinar").value.toString()
                loadedUser.items =
                    snapshot.child("items").value.toString()
                loadedUser.password =
                    snapshot.child("password").value.toString()
                loadedUser.point =
                    snapshot.child("point").value.toString().toInt()
                loadedUser.username =
                    snapshot.child("username").value.toString()
                loadedUser.wins =
                    snapshot.child("wins").value.toString().toInt()
                loadedUser.loses =
                    snapshot.child("loses").value.toString().toInt()
                loadedUser.email =
                    snapshot.child("email").value.toString()

                runBlocking {
                    userDao.insertUser(loadedUser)
                    Log.i("onDataChange_getUserAvatarId", "$snapshot")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("onCancelled_getAvatarId", error.message)
            }
        })
    }

    private fun fieldChecker() {
        _onPasswordError.value = inputPassword.value != inputRePassword.value
        _onInvalidEmail.value = !Patterns.EMAIL_ADDRESS.matcher(inputEmail.value.toString())
            .matches()
        _usernameLength.value = inputUsername.value?.length!! < 6
        _fieldChecker.value =
            usernameLength.value != true && onInvalidEmail.value != true && onPasswordError.value != true
    }

    fun resetErrorField() {
        _onPasswordError.value = false
        _onInvalidEmail.value = false
        _usernameLength.value = false
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