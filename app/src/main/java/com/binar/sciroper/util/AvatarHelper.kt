package com.binar.sciroper.util

import com.binar.sciroper.R

object AvatarHelper {

    fun provideList(): List<Int> {
        return listOf(
            avatarId1,
            avatarId2,
            avatarId3,
            avatarId4
        )
    }

    private const val avatarId1 = R.drawable.avatar21
    private const val avatarId2 = R.drawable.avatar22
    private const val avatarId3 = R.drawable.avatar23
    private const val avatarId4 = R.drawable.avatar24

}