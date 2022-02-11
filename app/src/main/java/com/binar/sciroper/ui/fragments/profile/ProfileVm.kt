package com.binar.sciroper.ui.fragments.profile

import androidx.lifecycle.*
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.AvatarHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ProfileVm(private val userDao: UserDAO) : ViewModel() {
    private val sharedPreference = AppSharedPreference
    private val uiScope = CoroutineScope(Dispatchers.Main + Job())
    val allOtherUsers: LiveData<List<User>> =
        userDao.getUserExclFlow(sharedPreference.id!!).asLiveData()
    private val _duplicateUsername = MutableLiveData<Boolean>()
    private val user = userDao.getUserById(sharedPreference.id!!)
    private val _userAvatarId = MutableLiveData<Int>()
    val userAvatarId: LiveData<Int> = _userAvatarId
    val avatarIds = AvatarHelper.provideList()

    fun getUser(): LiveData<User> {
        return user
    }

    fun setAvatarId(id: Int){
        _userAvatarId.value = id
    }
}

class ProfileVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileVm::class.java)) {
            return ProfileVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}