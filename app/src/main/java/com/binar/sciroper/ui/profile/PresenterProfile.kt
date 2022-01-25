package com.binar.sciroper.ui.profile

import android.util.Patterns.EMAIL_ADDRESS
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PresenterProfile(private val view: ProfileView) {
    private val idUser = AppSharedPreference.id!!
    private val emptyString = ""


    fun getDataUser() = App.appDb.getUserDao().getUserByIdProfile(idUser)

    private fun getAllUser() = App.appDb.getUserDao().getUserExcl(idUser)// todo bug
    private val passUser = getDataUser().password
    private val allUsername = getAllUser().username
    private val allEmail = getAllUser().email

    fun updateDataUser(
        avatar: Int,
        username: String,
        email: String,
        pass: String,
        newPass: String,
        reNewPass: String
    ) {

        GlobalScope.launch {
            when {
                pass != passUser -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("Check your password") }
                    }
                }
                !EMAIL_ADDRESS.matcher(email).matches() -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("Check your Email") }
                    }
                }
                newPass != reNewPass -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("New Password and Re-New Password not match") }
                    }
                }
                username == allUsername -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("Username already used") }
                    }
                }
                email == allEmail -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("Email already used") }
                    }
                }
                username == emptyString -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("You must have a username") }
                    }
                }

                pass == passUser && newPass == emptyString && reNewPass == emptyString -> {
                    val update = App.appDb.getUserDao()
                        .updateProfileById(
                            avatar,
                            username,
                            email,
                            pass,
                            idUser
                        )
                    launch(Dispatchers.Main) {
                        view.onSuccessUpdate(update)
                    }
                }

                pass == passUser && newPass != emptyString && reNewPass != emptyString -> {
                    val update = App.appDb.getUserDao()
                        .updateProfileById(
                            avatar,
                            username,
                            email,
                            reNewPass,
                            idUser
                        )
                    launch(Dispatchers.Main) {
                        view.onSuccessUpdate(update)

                    }
                }
            }
        }

    }
}
