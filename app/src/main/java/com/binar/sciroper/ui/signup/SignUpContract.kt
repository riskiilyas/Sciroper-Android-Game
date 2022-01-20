package com.binar.sciroper.ui.signup

import com.binar.sciroper.data.db.user.User

interface SignUpContract {

    interface View {
        fun onSignUpMsg(message: String)
        fun showProgress()
        fun onSuccess(username: String)
    }

    interface Presenter {
        fun onSignUp(userName: String, password: String)
    }


}