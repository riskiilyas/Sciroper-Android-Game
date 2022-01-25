package com.binar.sciroper.ui.profile

import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PresenterProfile(private val view: ProfileView) {
    private val idUser = AppSharedPreference.id!!

    private val allUsers = App.appDb.getUserDao().getUserExcl(AppSharedPreference.id!!)
    val player = App.appDb.getUserDao().getUserByIdNoLiveData(AppSharedPreference.id!!)
    var userAvatar: Int = App.appDb.getUserDao().getUserByIdNoLiveData(AppSharedPreference.id!!).avatarId

    fun getDataUser(): User {
        return player
    }

    private val passUser = player.password

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
                checkUserNameIsAlready(username) -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("Username already used") }
                    }
                }
                checkEmailIsAlready(email) -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("Email already used") }
                    }
                }
                username.isBlank() -> {
                    launch(Dispatchers.Main) {
                        view.run { onErrorUpdate("You must have a username") }
                    }
                }

                pass == passUser && newPass.isBlank() && reNewPass.isBlank() -> {
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

                pass == passUser && newPass.isNotBlank() && reNewPass.isNotBlank() -> {
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

    private fun checkEmailIsAlready(email: String): Boolean{
        allUsers.forEach {
            if (it.email == email) return true
        }
        return false
    }

    private fun checkUserNameIsAlready(username: String): Boolean{
        allUsers.forEach {
            if (it.username == username) return true
        }
        return false
    }
}
