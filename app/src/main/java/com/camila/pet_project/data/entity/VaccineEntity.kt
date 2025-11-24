package com.camila.pet_project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vaccine_table")
data class VaccineEntity(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val vaccineName: String,
    val description: String,
    val applicationDate: String,
)
