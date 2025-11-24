package com.camila.pet_project.domain.model

data class PetDomain(
    val id: Int = 0,
    val name: String = "",
    val type: String = "",
    val breed: String = "",
    val age: Int = 0,
    val photoUri: String = "",
    val address: String = "",
    val tutorPhone: String = "",
    val fivFelvStatus: String = "",
    val description: String = "",
    val userId: Int = 0
)

