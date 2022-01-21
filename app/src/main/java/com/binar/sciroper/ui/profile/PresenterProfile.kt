package com.binar.sciroper.ui.profile

import android.util.Patterns.EMAIL_ADDRESS
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PresenterProfile(private val view: ProfileView) {

    val idUser = AppSharedPreference.id
    fun getDataUser() = App.appDb.getUserDao().getUserByIdProfile(1)//ganti idnya
    fun updateDataUser(
        avatar: Int,
        username: String,
        email: String,
        oldPass: String,
        newPass: String,
        reNewPass: String
    ) {

        GlobalScope.launch {
            val passUser = getDataUser().password
            when {
                oldPass != passUser -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("Check your old Password") }
                    }
                }
                !EMAIL_ADDRESS.matcher(email).matches() -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("Check your Email") }
                    }
                }
                newPass != reNewPass -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("New Password and RePassword not match") }
                    }
                }

                else -> {
                    val update = App.appDb.getUserDao()
                        .updateProfileById(avatar, username, email, newPass, 1)
                    launch(Dispatchers.Main) {
                        view.onSuccessUpdate(update)

                    }
                }
            }
        }

    }
}
