package com.camila.pet_project.data.model

data class User(
    val id: Int,
    val name: String,
    val password: String,
    val petId: List<Int> = listOf(),
    val pet : MutableList<Pet> = mutableListOf()
)
