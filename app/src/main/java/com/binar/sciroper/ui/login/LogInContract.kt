package com.binar.sciroper.ui.login

import android.service.autofill.UserData
import com.binar.sciroper.data.db.user.User

interface LogInContract {

    interface View {
        fun onSignInMsg(message: String)
        fun showProgress()
        fun onSuccess(user: User)
    }

    interface Presenter {
        fun onSignUp(userName: String, password: String)
    }

}