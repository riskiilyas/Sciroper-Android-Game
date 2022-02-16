package com.binar.sciroper.ui.fragments.login

import android.util.Log
import androidx.lifecycle.*
import com.binar.sciroper.data.db.user.AuthDetails
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.firebase.FirebaseRtdb
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.data.retrofit.AuthResponse
import com.binar.sciroper.data.retrofit.Retrofit
import com.binar.sciroper.util.App
import com.binar.sciroper.util.BaseViewModel
import com.binar.sciroper.util.UiState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class LogInVM : BaseViewModel<UiState>() {

    private val sharedPreference: AppSharedPreference = AppSharedPreference
    val inputEmail = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()


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
                sharedPreference.isLogin = true
                sharedPreference.idBinar = loginResponse.data._id
                sharedPreference.isLogin = true
                sharedPreference.username = loginResponse.data.username
                sharedPreference.email = loginResponse.data.email
                uiState.value = UiState.Success(loginResponse)
                loadUser(loginResponse)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState.value = UiState.Error("${e.message}")
            }
        }
    }

    fun isLoggedIn() = sharedPreference.isLogin

    private suspend fun loadUser(idBinar: AuthResponse) {
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
                    uiState.value = UiState.Success(idBinar)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("onCancelled_getAvatarId", error.message)
            }
        })
    }
}

class LogInVMFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogInVM::class.java)) {
            return LogInVM() as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}