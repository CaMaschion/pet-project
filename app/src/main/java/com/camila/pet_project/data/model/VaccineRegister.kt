package com.camila.pet_project.data.model

data class VaccineRegister(
    val id: Int,
    val vaccineName: String,
    val description: String,
    val date: String,
    val petId: Int,
    val applicationAddress: String
)
