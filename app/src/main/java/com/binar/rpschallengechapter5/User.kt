package com.binar.rpschallengechapter5

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String
):Parcelable
