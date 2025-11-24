package com.camila.pet_project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    val userName: String,
    val password: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
