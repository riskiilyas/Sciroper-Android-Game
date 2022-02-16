package com.binar.sciroper.ui.fragments.achievement

import android.util.Log
import androidx.lifecycle.*
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.data.retrofit.HistoryResponse
import com.binar.sciroper.data.retrofit.Retrofit
import kotlinx.coroutines.launch
import java.lang.Exception

class AchievementVm : ViewModel() {

    private val sharedPreference = AppSharedPreference

    private val _loadInd = MutableLiveData<Boolean>()
    val loadInd: LiveData<Boolean> get() = _loadInd

    private val _historyList = MutableLiveData<List<HistoryResponse.History>>()
    val historyList: LiveData<List<HistoryResponse.History>> get() = _historyList

    private val _onError = MutableLiveData<String>()

    fun getHistory() {
        viewModelScope.launch {
            _loadInd.value = true
            try {
                val historyResponse =
                    Retrofit.retrofitService.getHistory("Bearer ${sharedPreference.userToken}")
                _historyList.value = historyResponse.data
                _loadInd.value = false
                Log.i("recycler_success", "${_historyList.value}")
            } catch (e: Exception) {
                _loadInd.value = false
                e.printStackTrace()
                _onError.value = "shit happens! ${e.message}"
                Log.i("recycler_fail", "${e.message}")
            }
        }
    }
}

class AchievementVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AchievementVm::class.java)) {
            return AchievementVm() as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}