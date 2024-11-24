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
    fun getUserByUserName(userName: String): Flow<User>

    @Query("SELECT * FROM user_table WHERE userName = :userName AND password = :password")
    fun getUserByUserNameAndPassword(userName: String, password: String): Flow<User>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(user: String, password: String)

}
