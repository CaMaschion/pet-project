package com.camila.pet_project.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.camila.pet_project.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table WHERE userName = :userName")
    fun getUserByUserName(userName: String): Flow<User>

    @Query("SELECT * FROM user_table WHERE password = :password")
    fun getUserByPassword(password: String): Flow<User>

    @Query("SELECT * FROM user_table WHERE userName = :userName AND password = :password")
    fun getUserByUserNameAndPassword(userName: String, password: String): Flow<User>

    suspend fun insertUser(user: User)

}
