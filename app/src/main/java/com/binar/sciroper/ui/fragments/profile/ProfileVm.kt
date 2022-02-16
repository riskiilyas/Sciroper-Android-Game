package com.binar.sciroper.ui.fragments.profile

import android.util.Log
import androidx.lifecycle.*
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.firebase.FirebaseRtdb
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.data.retrofit.AuthResponse
import com.binar.sciroper.data.retrofit.Retrofit
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ProfileVm(private val userDao: UserDAO) : ViewModel() {
    private val sharedPreference = AppSharedPreference
    private val user = userDao.getUserByIdNoLiveData(sharedPreference.idBinar!!)
    val userLive = userDao.getUserById(sharedPreference.idBinar!!)
    private val _userAvatarId = MutableLiveData<Int>()
    val userAvatarId: LiveData<Int> = _userAvatarId
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email
    private val userToken = "Bearer ${sharedPreference.userToken}"
    private val _onSuccess = MutableLiveData<Boolean>()
    val onSuccess: LiveData<Boolean> get() = _onSuccess
    private val _onFailure = MutableLiveData<String>()
    val onFailure: LiveData<String> get() = _onFailure
    private val _userDetails = MutableLiveData<AuthResponse>()
    val userDetails: LiveData<AuthResponse> get() = _userDetails
    private val database = FirebaseRtdb()
    private val _loadingInd = MutableLiveData<Boolean>()
    val loadingInd: LiveData<Boolean> get() = _loadingInd

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        _loadingInd.value = true
        viewModelScope.launch {
            try {
                val userDetails = Retrofit.retrofitService.getUser("$userToken")
                _userDetails.value = userDetails
                _loadingInd.value = false
                Log.i("banana_menu_success", "shit actually works! $userDetails")
            } catch (e: Exception) {
                _loadingInd.value = false
                e.printStackTrace()
                _onFailure.value = e.message
                Log.i("banana_menu_failure", "${e.message}, token: $userToken")
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch { userDao.deleteUser(user) }
    }

    fun postChanges(
        token: String,
        requestBody: RequestBody,
        username: String,
        email: String,
        avatarId: Int
    ) {
        _loadingInd.value = true
        viewModelScope.launch {
            try {
                Retrofit.retrofitService.updateProfile(
                    token,
                    requestBody
                )
                postChangesFirebase(username = username, email = email, avatarId = avatarId)
                _onSuccess.value = true
                _loadingInd.value = false
                Log.i("banana_update", "post to retrofit success")
                Log.i("banana_update", "attempting to update firebase")
            } catch (e: Exception) {
                e.printStackTrace()
                _onFailure.value = "Fails to updated"
                _loadingInd.value = false
                Log.i("banana_update", "${e.message}")
            }
        }

        viewModelScope.launch {
            userDao.updateUser(userLive.value!!.apply {
                this.username = username
                this.email = email
                this.avatarId = avatarId
            })
        }

    }

    private fun postChangesFirebase(username: String, email: String, avatarId: Int) {
        Log.i("banana_update", "attempting to update firebase")
        val userMap = mutableMapOf<String, Any>()
        userMap["email"] = email
        userMap["username"] = username
        userMap["avatarId"] = avatarId

        database.database.getReference("Users").child(sharedPreference.idBinar!!)
            .updateChildren(userMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("update_success", "you got it boss")
                } else {
                    Log.i("update_error", "${it.exception}")
                }
            }
    }

    fun setOnSuccessValue() {
        _onSuccess.value = false
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