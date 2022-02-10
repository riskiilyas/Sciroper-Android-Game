package com.binar.sciroper.ui.fragments.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference

class SettingVm(private val userDao: UserDAO, private val sharedPreference: AppSharedPreference) :
    ViewModel() {
    private val _isChecked = MutableLiveData<Boolean>(false)
    val isChecked: LiveData<Boolean> = _isChecked

    fun setIsChecked(){
        _isChecked.value = !_isChecked.value!!
    }

    fun setTheme(condition: Boolean) {
        AppSharedPreference.isDarkMode = condition
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