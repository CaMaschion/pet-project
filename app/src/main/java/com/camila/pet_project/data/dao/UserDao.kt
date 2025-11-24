package com.camila.pet_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camila.pet_project.data.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table WHERE userName = :userName")
    suspend fun getUserByUserName(userName: String): UserEntity?

    @Query("SELECT * FROM user_table WHERE userName = :userName AND password = :password")
    suspend fun getUserByUserNameAndPassword(userName: String, password: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)
}
