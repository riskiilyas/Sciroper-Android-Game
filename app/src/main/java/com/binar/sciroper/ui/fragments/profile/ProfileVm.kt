package com.binar.sciroper.ui.fragments.profile

import android.util.Log
import androidx.lifecycle.*
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.firebase.FirebaseRtdb
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.data.retrofit.AuthResponse
import com.binar.sciroper.data.retrofit.Retrofit
import com.binar.sciroper.util.AvatarHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ProfileVm(private val userDao: UserDAO) : ViewModel() {
    private val sharedPreference = AppSharedPreference
    private val uiScope = CoroutineScope(Dispatchers.Main + Job())
    val allOtherUsers: LiveData<List<User>> =
        userDao.getUserExclFlow(sharedPreference.id!!).asLiveData()
    private val _duplicateUsername = MutableLiveData<Boolean>()
    private val user = userDao.getUserById(sharedPreference.id!!)
    private val _userAvatarId = MutableLiveData<Int>()
    val userAvatarId: LiveData<Int> = _userAvatarId
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email
    private val userToken = "Bearer ${sharedPreference.userToken}"
    private val _onSuccess = MutableLiveData<AuthResponse>()
    val onSuccess: LiveData<AuthResponse> get() = _onSuccess
    private val _onFailure = MutableLiveData<String>()
    val onFailure: LiveData<String> get() = _onFailure
    private val _userDetails = MutableLiveData<AuthResponse>()
    val userDetails: LiveData<AuthResponse> get() = _userDetails
    private val _postRetrofitToast = MutableLiveData<Boolean>()
    val postRetrofitToast: LiveData<Boolean> get() = _postRetrofitToast
    private val _postFirebaseToast = MutableLiveData<Boolean>()
    val postFirebaseToast: LiveData<Boolean> get() = _postFirebaseToast
    private val emailSize: Int = 0

    val avatarIds = AvatarHelper.provideList()

    private val database = FirebaseRtdb()

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            try {
                val userDetails = Retrofit.retrofitService.getUser("$userToken")
                _onSuccess.value = userDetails
                _userDetails.value = userDetails
                Log.i("banana_menu_success", "shit actually works! ${userDetails}")
            } catch (e: Exception) {
                e.printStackTrace()
                _onFailure.value = e.message
                Log.i("banana_menu_failure", "${e.message}, token: $userToken")
            }
        }
    }

//    fun postChanges(
//        token: String,
//        requestBody: RequestBody,
//    ) {
//        viewModelScope.launch {
//            try {
//                Retrofit.retrofitService.updateProfile(
//                    token,
//                    requestBody
//                )
//                _postRetrofitToast.value = true
//                Log.i("banana_update", "posting success")
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Log.i("banana_update", "${e.message}")
//            }
//        }
//    }

    fun postChanges(
        token: String,
        requestBody: RequestBody,
    ) {
        viewModelScope.launch {
            try {
                Retrofit.retrofitService.updateProfile(
                    token,
                    requestBody
                )
                _postRetrofitToast.value = true
                Log.i("banana_update", "post to retrofit success")
                Log.i("banana_update", "attempting to update firebase")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("banana_update", "${e.message}")
            }
        }
    }

    fun setRetrofitToast() {
        _postRetrofitToast.value = false
    }

    fun setFirebaseToast() {
        _postRetrofitToast.value = false
    }

    fun setAvatarId(avatarId: Int) {
        _userAvatarId.value = avatarId
    }

    fun postChangesFirebase(idBinar: String, username: String, email: String, avatarId: Int) {
        val userMap = mutableMapOf<String, Any>()
        userMap["email"] = email
        userMap["username"] = username
        userMap["avatarId"] = avatarId

        database.database.getReference("Users").child(idBinar).updateChildren(userMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _postFirebaseToast.value = true
                    setFirebaseToast()
                    Log.i("update_success", "you got it boss")
                } else {
                    Log.i("update_error", "${it.exception}")
                }
            }
    }

    fun getEmailSize(email: String) {
        val checkEmail =
            database.database.getReference("Users").orderByChild("email").equalTo(email)
        checkEmail.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("check_duplicate", "${snapshot.childrenCount}")

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("check_duplicate", "can't get query size with reference to email")
            }
        })
    }
//    fun getUserDetails() {
//        val target = database.database.getReference("Users").orderByChild("idBinar")
//            .equalTo(sharedPreference.idBinar)
//        target.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.i("banana_profile", "$snapshot")
//                _userAvatarId.value =
//                    snapshot.child(sharedPreference.idBinar!!).child("avatarId").value.toString()
//                        .toInt()
//                _username.value = snapshot.child(sharedPreference.idBinar!!).child("username").value.toString()
//                _email.value = snapshot.child(sharedPreference.idBinar!!).child("email").value.toString()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.i("onCancelled_getUserDetails", error.message)
//            }
//        })
//    }
}

class ProfileVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileVm::class.java)) {
            return ProfileVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}