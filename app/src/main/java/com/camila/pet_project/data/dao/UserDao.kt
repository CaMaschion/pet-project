package com.camila.pet_project.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.camila.pet_project.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getUserById(userId: Int): Flow<User>

}
