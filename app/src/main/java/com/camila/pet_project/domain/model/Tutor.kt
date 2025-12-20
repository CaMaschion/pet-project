package com.camila.pet_project.domain.model

data class Tutor(
    val id: Int = 0,
    val name: String,
    val email: String,
    val passwordHash: String
)

