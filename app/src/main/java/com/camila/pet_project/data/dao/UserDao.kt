package com.camila.pet_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camila.pet_project.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table WHERE userName = :userName")
    suspend fun getUserByUserName(userName: String): User

    @Query("SELECT * FROM user_table WHERE userName = :userName AND password = :password")
    suspend fun getUserByUserNameAndPassword(userName: String, password: String): User

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)
}
