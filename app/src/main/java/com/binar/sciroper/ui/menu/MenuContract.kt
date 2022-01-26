package com.binar.sciroper.ui.menu

import androidx.lifecycle.LiveData
import com.binar.sciroper.data.db.user.User

interface MenuContract {

    interface View
    interface Presenter {
        fun getUser(id: Int): LiveData<User>
    }
}