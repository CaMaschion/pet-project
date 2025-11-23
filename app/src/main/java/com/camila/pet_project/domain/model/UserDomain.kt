package com.camila.pet_project.domain.model

/**
 * Domain model for User
 * Represents the user in the business logic layer
 */
data class UserDomain(
    val id: Int = 0,
    val userName: String,
    val password: String
)

