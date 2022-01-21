package com.binar.sciroper.ui.profile

import android.util.Patterns.EMAIL_ADDRESS
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PresenterProfile(private val view: ProfileView) {
    private val idUser = AppSharedPreference.id!!//todo ini idUsernya


    fun getDataUser() = App.appDb.getUserDao().getUserByIdProfile(1)// todo tinggal ganti ke idUser
    private fun getAllUser() = App.appDb.getUserDao().getUserExcl(1)// todo tinggal ganti ke idUser
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
                username == "" -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("You must have a username") }
                    }
                }

                pass == passUser && newPass == "" && reNewPass == "" -> {
                    val update = App.appDb.getUserDao()
                        .updateProfileById(
                            avatar,
                            username,
                            email,
                            pass,
                            1
                        )
                    // todo tinggal ganti ke idUser
                    launch(Dispatchers.Main) {
                        view.onSuccessUpdate(update)
                    }
                }

                pass == passUser && newPass != "" && reNewPass != "" -> {
                    val update = App.appDb.getUserDao()
                        .updateProfileById(
                            avatar,
                            username,
                            email,
                            reNewPass,
                            1
                        )// todo tinggal ganti ke idUser
                    launch(Dispatchers.Main) {
                        view.onSuccessUpdate(update)

                    }
                }
            }
        }

    }
}
