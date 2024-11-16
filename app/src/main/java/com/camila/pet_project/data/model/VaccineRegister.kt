package com.camila.pet_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vaccine_register_table")
data class VaccineRegister(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val vaccineName: String,
    val description: String,
    val date: String,
    val petId: Int,
    val applicationAddress: String
)
