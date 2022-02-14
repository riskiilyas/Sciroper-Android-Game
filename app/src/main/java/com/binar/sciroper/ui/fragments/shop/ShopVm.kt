package com.binar.sciroper.ui.fragments.shop

import androidx.lifecycle.*
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.firebase.FirebaseRtdb
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.App
import com.binar.sciroper.util.checkNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class ShopVm(private val userDao: UserDAO) : ViewModel() {
    val userData = App.appDb.getUserDao().getUserById(AppSharedPreference.idBinar!!)

    fun buyItem(id: Char, price: Int){
        val strBuilder = StringBuilder(userData.value!!.items).append(id)
        val updateUser = userData.value!!.apply {
            items = strBuilder.toString()
            coin -= price
        }
        viewModelScope.launch(Dispatchers.Default) {
            userDao.updateUser(updateUser)
        }
        checkNetworkAvailable {
            if (it) FirebaseRtdb().updateUser(updateUser)
        }
    }

}

class ShopVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopVm::class.java)) {
            return ShopVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}