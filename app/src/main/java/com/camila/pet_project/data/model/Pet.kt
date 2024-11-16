package com.camila.pet_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet_table")
data class Pet (
    @PrimaryKey (autoGenerate = true) val id: Int,
    val name: String = "",
    val type: String = "",
    val breed: String = "",
    val age: Int = 0,
    val description: String = "",
    val userId: Int = 0,
    val vaccines: MutableList<Vaccine> = mutableListOf()
)
