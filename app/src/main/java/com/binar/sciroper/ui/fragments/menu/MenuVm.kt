package com.binar.sciroper.ui.fragments.menu

import android.util.Log
import androidx.lifecycle.*
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.firebase.FirebaseRtdb
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.data.retrofit.AuthResponse
import com.binar.sciroper.data.retrofit.Retrofit
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class MenuVm(userDao: UserDAO) : ViewModel() {
    private val sharedPreferences = AppSharedPreference

    private val userId = sharedPreferences.idBinar
    private val _user = userDao.getUserById(sharedPreferences.idBinar!!)
    val user get() = _user
    private val database = FirebaseRtdb()

    private val _onSuccess = MutableLiveData<AuthResponse>()
    val onSuccess: LiveData<AuthResponse> get() = _onSuccess
    private val _onFailure = MutableLiveData<String>()
    val onFailure: LiveData<String> get() = _onFailure
    private val _userDetails = MutableLiveData<AuthResponse>()
    val userDetails: LiveData<AuthResponse> get() = _userDetails
    private val _avatarId = MutableLiveData<Int>()
    val avatarId: LiveData<Int> get() = _avatarId
    private val _userLevel = MutableLiveData<Int>()
    val userLevel: LiveData<Int> get() = _userLevel
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val userToken = "Bearer ${sharedPreferences.userToken}"

    fun updateUser(user: LiveData<User>) {
        user.value?.let { FirebaseRtdb().updateUser(it) }
    }

}

class MenuVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuVm::class.java)) {
            return MenuVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}