package com.binar.sciroper.ui.signup

import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SignUpPresenter(private val view: SignUpContract.View): SignUpContract.Presenter {

    override fun register(username: String, email: String, password: String, avatarId: Int) {
        GlobalScope.launch(Dispatchers.IO) { // background thread
            val dao = App.appDb.getUserDao()
            val getUserAndEmail = dao.getUserByUsernameAndEmail(username, email)

            if (getUserAndEmail == null) {
                dao.insertUser(
                    User(
                        username = username,
                        email = email,
                        password = password,
                        avatarId = avatarId
                    )
                )
            }

            launch(Dispatchers.Main) { // main thread
                if (getUserAndEmail == null) {
                    view.onSuccess(username)
                } else {
                    view.onSignUpMsg("Username atau email telah terdaftar!")
                }
            }
        }
    }

    override fun getUser(email: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val user =
                async {
                    App.appDb.getUserDao().getUserByEmailAndPassword(email, password)
                }.await()
            AppSharedPreference.id = user.id
            AppSharedPreference.isLogin = true
        }
    }
}