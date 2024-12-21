package com.camila.pet_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")

data class User(
    val userName: String,
    val password: String,
//    val petId: List<Int> = listOf(),
//    val pet: MutableList<Pet> = mutableListOf()
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
