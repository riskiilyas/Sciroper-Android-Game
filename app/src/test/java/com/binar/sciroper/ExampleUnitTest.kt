package com.binar.sciroper

import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.util.AvatarHelper
import com.binar.sciroper.util.UserLevel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var user: User
    private lateinit var userLevel: UserLevel

    @Before
    fun setup() {
        user = User(
            1, "Riski", "riski@gmail.com", "riski123", AvatarHelper.avatarId1, 3, 1, 5, 90
        )

        userLevel = UserLevel(user)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun avatarHelper_isCorrect() {
        assert( AvatarHelper.avatarId3 == R.drawable.avatar3 )
    }

    @Test
    fun userLevelWin_isCorrect(){
        userLevel.win()
        assert( user.point == 15 && user.level == 6)
    }

    @Test
    fun userLevelLose_isCorrect(){
        userLevel.lose()
        assert( user.point == 35 )
    }

    @Test
    fun userLevelSortUsers_isCorrect(){

    }
}