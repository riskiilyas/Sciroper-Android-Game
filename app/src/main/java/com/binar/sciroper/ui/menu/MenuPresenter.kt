package com.binar.sciroper.ui.menu

import androidx.lifecycle.LiveData
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.util.App

class MenuPresenter(private val view: MenuContract.View) : MenuContract.Presenter {

    override fun getUser(id: Int): LiveData<User> {
        return App.appDb.getUserDao().getUserById(id)
    }

}