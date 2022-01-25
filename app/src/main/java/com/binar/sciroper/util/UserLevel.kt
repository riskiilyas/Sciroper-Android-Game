package com.binar.sciroper.util

import com.binar.sciroper.data.db.user.User
import java.util.*

class UserLevel(private val user: User) {

    companion object {

        fun sortUsersLevel(users: List<User>):List<User>{
            Collections.sort(users,
                Comparator { p1, p0 ->
                    when {
                        p0.level == p1.level -> {
                            when {
                                p0.point == p1.point -> return@Comparator 0
                                p0.point > p1.point -> return@Comparator 1
                                else -> return@Comparator -1
                            }
                        }
                        p0.level > p1.level -> return@Comparator 1
                        else -> return@Comparator -1
                    }

                })
            return users
        }

    }

    fun win() {
        user.point += 25
        user.wins += 1
        checkPoint()
    }

    fun lose() {
        user.point += 10
        user.loses += 1
        checkPoint()
    }

    private fun checkPoint() {
        if (user.point >= 100) {
            upLevel()
        }
    }

    private fun upLevel() {
        user.level += (user.point / 100)
        user.point -= 100
    }

}