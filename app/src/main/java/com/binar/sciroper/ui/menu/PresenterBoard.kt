package com.binar.sciroper.ui.menu

import com.binar.sciroper.util.App

class PresenterBoard (private val view: MainView)  {
        fun getData(id: Int) = App.appDb.getUserDao().getUsers()
}