package com.binar.sciroper.ui.fragments.menu

import androidx.lifecycle.*
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.firebase.FirebaseRtdb
import com.binar.sciroper.data.local.AppSharedPreference

class MenuVm(userDao: UserDAO) : ViewModel() {
    private val sharedPreferences = AppSharedPreference

    private val _user = userDao.getUserById(sharedPreferences.idBinar!!)
    val user get() = _user

    private val _avatarId = MutableLiveData<Int>()
    val avatarId: LiveData<Int> get() = _avatarId
    private val _userLevel = MutableLiveData<Int>()
    val userLevel: LiveData<Int> get() = _userLevel
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username


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