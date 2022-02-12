package com.binar.sciroper.ui.fragments.setting

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.data.local.MusicPlayer.mediaPlayer

class SettingVm(private val userDao: UserDAO, private val sharedPreference: AppSharedPreference) :
    ViewModel() {
    private val _isChecked = MutableLiveData<Boolean>(AppSharedPreference.isDarkMode)
    private val _isCheckedMusic = MutableLiveData<Boolean>(AppSharedPreference.isMusicPlay)
    private val _isCheckedNotif = MutableLiveData<Boolean>(AppSharedPreference.isNotif)
    val isChecked: LiveData<Boolean> = _isChecked
    val isCheckedMusic: LiveData<Boolean> = _isCheckedMusic
    val isCheckedNotif: LiveData<Boolean> = _isCheckedNotif

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
    fun setIsCheckedNotif() {
        _isCheckedNotif.value = !_isCheckedNotif.value!!
    }

    fun setNotif(condition: Boolean) {
        AppSharedPreference.isNotif = condition
    }

}

fun playMusic(context: Context) {
    mediaPlayer = MediaPlayer.create(
        context,
        R.raw.bensound_ukulele
    )
    mediaPlayer?.start()
    mediaPlayer?.isLooping = true
}

 fun pausePlay() {
    mediaPlayer?.stop()
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