package com.binar.sciroper.ui.fragments.leaderboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.firebase.FirebaseRtdb
import com.binar.sciroper.util.UserLevel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class LeaderBoardVm : ViewModel() {

    private val database = FirebaseRtdb()
    private val _userList = mutableListOf<User>()
    val userList get() = _userList
    private val _userLiveData = MutableLiveData<List<User>>()
    val userLiveData: LiveData<List<User>> get() = _userLiveData

    private val _userListSize = MutableLiveData<Int>()
    val userListSize: LiveData<Int> get() = _userListSize
    private var observer: () -> Unit = {}

    init {
        getUsers()
    }

    private fun getUsers() {
        database.databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.removeAll(userList)
                for (data in snapshot.children) {
                    val user = data.getValue(User::class.java)
                    _userList.add(user!!)
                }
                _userLiveData.value = UserLevel.sortUsersLevel(userList)
                _userListSize.value = userList.size
                observe()
                Log.i("userlist_size", "${userListSize.value}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("update_list", "shit happens")
            }
        })
    }

    fun setObserver(callback: () -> Unit) {
        observer = callback
    }

    fun observe() {
        observer()
    }

}

class LeaderBoardVmFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderBoardVm::class.java)) {
            return LeaderBoardVm() as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}