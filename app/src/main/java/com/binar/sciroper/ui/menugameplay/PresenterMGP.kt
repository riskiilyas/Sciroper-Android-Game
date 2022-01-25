package com.binar.sciroper.ui.menugameplay

import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.App


class PresenterMGP(private val view: MGPView) {
    private val idUser = AppSharedPreference.id!!

    fun getDataUser() =
        App.appDb.getUserDao().getUserByIdNoLiveData(idUser)
}