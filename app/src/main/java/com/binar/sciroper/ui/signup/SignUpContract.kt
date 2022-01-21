package com.binar.sciroper.ui.signup


interface SignUpContract {

    interface View {
        fun onSignUpMsg(message: String)
        fun showProgress()
        fun onSuccess(username: String)
    }

    interface Presenter {
        fun register(username: String, email: String, password: String, avatarId: Int)
        fun getUser(email: String, password: String)
    }


}