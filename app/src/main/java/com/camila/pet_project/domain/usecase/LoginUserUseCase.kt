package com.camila.pet_project.domain.usecase

import com.camila.pet_project.domain.model.UserDomain
import com.camila.pet_project.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Use Case for user login
 * Following Single Responsibility Principle (SOLID)
 */
class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    /**
     * Executes login operation
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

            // Authenticate user
            val user = userRepository.authenticateUser(userName, password)

            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Invalid username or password"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

