package com.binar.sciroper.data.local

import android.content.Context
import com.binar.sciroper.util.App

object AppSharedPreference {

    private const val APP_SHARED_PREFERENCES = "app_shared_preferences"
    private const val KEY_IS_LOGIN = "key_is_login"
    private const val KEY_IS_DARK_MODE = "key_is_dark_mode"
    private const val KEY_ID = "key_id"
    private const val KEY_MUSIC = "key_music"
    private const val KEY_USERNAME = "key_username"
    private const val NOTIF = "notif"
    private const val USER_TOKEN = "user_token"
    private const val ID_BINAR = "id_binar"
    private const val KEY_EMAIL = "key_email"


    private val sharedPreference =
        App.context.get()?.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    var isLogin: Boolean?
        get() = sharedPreference?.getBoolean(KEY_IS_LOGIN, false)
        set(value) {
            if (value != null) {
                sharedPreference?.edit()?.putBoolean(KEY_IS_LOGIN, value)?.apply()
            }
        }

    var id: Int?
        get() = sharedPreference?.getInt(KEY_ID, 0)
        set(value) {
            if (value != null) {
                sharedPreference?.edit()?.putInt(KEY_ID, value)?.apply()
            }
        }

    var username: String?
        get() = sharedPreference?.getString(KEY_USERNAME, "")
        set(value) {
            sharedPreference?.edit()?.putString(KEY_USERNAME, value)?.apply()
        }

    var isDarkMode: Boolean?
        get() = sharedPreference?.getBoolean(KEY_IS_DARK_MODE, false)
        set(value) {
            if (value != null) {
                sharedPreference?.edit()?.putBoolean(KEY_IS_DARK_MODE, value)?.apply()
            }
        }

    var isMusicPlay: Boolean
        get() = sharedPreference?.getBoolean(KEY_MUSIC, true) ?: false
        set(value) {
            if (value != null) {
                sharedPreference?.edit()?.putBoolean(KEY_MUSIC, value)?.apply()
            }
        }

    var isNotif: Boolean?
        get() = sharedPreference?.getBoolean(NOTIF, true)
        set(value) {
            if (value != null) {
                sharedPreference?.edit()?.putBoolean(NOTIF, value)?.apply()
            }
        }
    var userToken: String?
        get() = sharedPreference?.getString(USER_TOKEN, "")
        set(value) {
            if (value != null) {
                sharedPreference?.edit()?.putString(USER_TOKEN, value)?.apply()
            }
        }

    var idBinar: String?
        get() = sharedPreference?.getString(ID_BINAR, "")
        set(value) {
            if (value != null) {
                sharedPreference?.edit()?.putString(ID_BINAR, value)?.apply()
            }
        }

    var email: String?
        get() = sharedPreference?.getString(KEY_EMAIL, "")
        set(value) {
            if (value != null) {
                sharedPreference?.edit()?.putString(KEY_EMAIL, value)?.apply()
            }
        }
}