package com.binar.sciroper.ui.fragments.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference

class SettingVm(private val userDao: UserDAO, private val sharedPreference: AppSharedPreference) :
    ViewModel() {
    private val _isChecked = MutableLiveData<Boolean>(AppSharedPreference.isDarkMode)
    private val _isCheckedNotif = MutableLiveData<Boolean>(AppSharedPreference.isNotif)
    val isChecked: LiveData<Boolean> = _isChecked
    val isCheckedNotif: LiveData<Boolean> = _isCheckedNotif

    fun setIsChecked() {
        _isChecked.value = !_isChecked.value!!
    }

    fun setTheme(condition: Boolean) {
        AppSharedPreference.isDarkMode = condition
    }

    fun setMusic(condition: Boolean) {
        AppSharedPreference.isMusicPlay = condition
    }

    fun setIsCheckedNotif() {
        _isCheckedNotif.value = !_isCheckedNotif.value!!
    }

    fun setNotif(condition: Boolean) {
        AppSharedPreference.isNotif = condition
    }

}


class SettingVmFactory(
    private val userDao: UserDAO,
    private val sharedPreference: AppSharedPreference
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingVm::class.java)) {
            return SettingVm(userDao, sharedPreference) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}