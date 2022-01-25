package com.binar.sciroper.ui.leaderboard

import com.binar.sciroper.util.App

class PresenterBoard (private val view: MainView)  {
        fun getData() = App.appDb.getUserDao().getUsers()
}