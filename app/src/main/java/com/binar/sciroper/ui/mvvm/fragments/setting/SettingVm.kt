package com.binar.sciroper.ui.mvvm.fragments.setting

import android.content.Context
import android.media.MediaPlayer
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference


class SettingVm(private val userDao: UserDAO, private val sharedPreference: AppSharedPreference) :
    ViewModel() {
    private val _isChecked = MutableLiveData<Boolean>(false)
    private val _isCheckedMusic = MutableLiveData<Boolean>(false)
    val isChecked: LiveData<Boolean> = _isChecked
    val isCheckedMusic: LiveData<Boolean> = _isCheckedMusic

    fun setIsChecked() {
        _isChecked.value = !_isChecked.value!!
    }

    fun setTheme(condition: Boolean) {
        AppSharedPreference.isDarkMode = condition
    }

    fun setIsCheckedMusic() {
        _isCheckedMusic.value = !_isCheckedMusic.value!!
    }

    fun setMusic(condition: Boolean) {
        AppSharedPreference.isMusicPlay = condition
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