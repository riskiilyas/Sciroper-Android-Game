package com.binar.sciroper.util

import com.binar.sciroper.R

object AvatarHelper {

    fun provideListFull(): List<Int> {
        return listOf(
            avatarId1,
            avatarId2,
            avatarId3,
            avatarId4,
            avatarI1,
            avatarI2,
            avatarI3,
            avatarI4,
            avatarI5,
            avatarI6,
            avatarI7,
            avatarI8
        )
    }

    fun provideList(): List<Int> {
        return listOf(
            avatarId1,
            avatarId2,
            avatarId3,
            avatarId4,
        )
    }

    private const val avatarId1 = R.drawable.avatar21
    private const val avatarId2 = R.drawable.avatar22
    private const val avatarId3 = R.drawable.avatar23
    private const val avatarId4 = R.drawable.avatar24
    private const val avatarI1 = R.drawable.avatar31
    private const val avatarI2 = R.drawable.avatar32
    private const val avatarI3 = R.drawable.avatar33
    private const val avatarI4 = R.drawable.avatar34
    private const val avatarI5 = R.drawable.avatar41
    private const val avatarI6 = R.drawable.avatar42
    private const val avatarI7 = R.drawable.avatar43
    private const val avatarI8 = R.drawable.avatar44
}