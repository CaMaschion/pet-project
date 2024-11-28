package com.camila.pet_project.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")

data class User(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val userName: String,
    val password: String,
//    val petId: List<Int> = listOf(),
//    val pet: MutableList<Pet> = mutableListOf()
)
