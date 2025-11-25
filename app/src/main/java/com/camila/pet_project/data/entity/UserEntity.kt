package com.camila.pet_project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    val userName: String,
    val password: String,
    val name: String = "",
    val contactInfo: String = "",
    val address: String = "",
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
