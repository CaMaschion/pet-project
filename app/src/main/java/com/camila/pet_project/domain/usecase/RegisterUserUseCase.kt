package com.camila.pet_project.domain.usecase

import com.camila.pet_project.domain.model.UserDomain
import com.camila.pet_project.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Use Case for user registration
 * Following Single Responsibility Principle (SOLID)
 */
class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    /**
     * Executes registration operation
     * @return Result with UserDomain on success, or error message on failure
     */
    suspend operator fun invoke(userName: String, password: String): Result<UserDomain> {
        return try {
            // Validate inputs
            if (userName.isBlank()) {
                return Result.failure(Exception("Username cannot be empty"))
            }

            if (password.isBlank()) {
                return Result.failure(Exception("Password cannot be empty"))
            }

            if (password.length < 4) {
                return Result.failure(Exception("Password must be at least 4 characters"))
            }

            // Check if user already exists
            val existingUser = userRepository.getUserByUserName(userName)
            if (existingUser != null) {
                return Result.failure(Exception("Username already exists"))
            }

            // Register new user
            userRepository.registerUser(userName, password)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

