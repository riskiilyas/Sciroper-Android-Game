package com.binar.sciroper.ui.profile

import android.os.Message
import android.widget.ImageView

interface ProfileView {
    fun onSuccessUpdate(user: Int)
    fun onErrorUpdate(message: String)
    fun onSignOut()
    fun choiceAvatar(user: Array<ImageView>)

}