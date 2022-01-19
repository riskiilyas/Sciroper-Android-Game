package com.binar.sciroper.ui.signup


import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.util.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpPresenter(private val view: SignUpContract.View) {

    fun register(username: String, email: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) { // background thread
            val dao = App.appDb.getUserDao()
            val getUserAndEmail = dao.getUserByUsernameAndEmail(username, email)

            if (getUserAndEmail == null) {
                dao.insertUser(User(username = username, email = email, password = password))
//                view.onSuccess(getUserAndEmail)
            }

            launch(Dispatchers.Main) { // main thread
                if (getUserAndEmail == null) {
                    view.onSignUpMsg("Registrasi berhasil")
                } else {
                    view.onSignUpMsg("Username atau email telah terdaftar!")
                }
            }
        }
    }
}