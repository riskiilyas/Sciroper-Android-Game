package com.binar.sciroper.ui.login

import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.App
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class LogInPresenter(private val view: LogInContract.View) : LogInContract.Presenter {

    override fun onLogin(email: String, password: String) {
        GlobalScope.launch {
            val dao = App.appDb.getUserDao()
            val getUserAndEmail = dao.getUserByEmailAndPassword(email = email, password = password)
            launch(Dispatchers.Main) {
                if (getUserAndEmail == null) {
                    view.onSignInMsg("Incorrect email or password")
                } else {
                    AppSharedPreference.id = getUserAndEmail.id
                    AppSharedPreference.isLogin = true
                    view.onSuccess(getUserAndEmail)
                }
            }
        }
    }
}