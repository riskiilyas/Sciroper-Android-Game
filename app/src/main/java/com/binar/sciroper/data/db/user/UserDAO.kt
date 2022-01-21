package com.binar.sciroper.data.db.user

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface UserDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insertUser(user: User): Long

    @Delete
    suspend fun deleteUser(user: User): Int

    @Update
    suspend fun updateUser(user: User): Int

    @Query("SELECT * FROM ${User.TABLE_NAME} WHERE id = :id")
    fun getUserById(id: Int): LiveData<User>

    @Query("SELECT * FROM ${User.TABLE_NAME} WHERE email = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password: String): LiveData<User>

    @Query("DELETE FROM ${User.TABLE_NAME}")
    suspend fun clearAllUser(): Int

    @Query("SELECT * FROM ${User.TABLE_NAME}")
    fun getUsers(): LiveData<List<User>>

    @Query("UPDATE ${User.TABLE_NAME} SET avatar_id = :avatarId, username = :username, email= :email,password = :newPass WHERE id = :id")
    fun updateProfileById(avatarId: Int, username: String, email: String, newPass: String, id: Int): Int

    @Query("SELECT * FROM ${User.TABLE_NAME} WHERE id = :id")
    fun getUserByIdProfile(id: Int): User

}