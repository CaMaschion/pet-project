package com.camila.pet_project.domain.repository

import com.camila.pet_project.domain.model.UserDomain

/**
 * Repository interface for User operations
 * Following Repository Pattern and Dependency Inversion Principle (SOLID)
 */
interface UserRepository {

    /**
     * Checks if a user exists by username
     */
    suspend fun getUserByUserName(userName: String): UserDomain?

    /**
     * Authenticates user with username and password
     */
    suspend fun authenticateUser(userName: String, password: String): UserDomain?

    /**
     * Registers a new user
     * @throws Exception if user already exists
     */
    suspend fun registerUser(userName: String, password: String): Result<UserDomain>
}

