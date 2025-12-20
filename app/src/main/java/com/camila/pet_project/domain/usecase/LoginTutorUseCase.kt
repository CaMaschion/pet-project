package com.camila.pet_project.domain.usecase

import com.camila.pet_project.domain.model.Tutor
import com.camila.pet_project.domain.repository.TutorRepository
import java.security.MessageDigest
import javax.inject.Inject

/**
 * Use Case for tutor login
 * Following Single Responsibility Principle (SOLID)
 */
class LoginTutorUseCase @Inject constructor(
    private val tutorRepository: TutorRepository
) {
    /**
     * Executes login operation
     * @return Result with Tutor on success, or error message on failure
     */
    suspend operator fun invoke(email: String, password: String): Result<Tutor> {
        return try {
            // Validate inputs
            if (email.isBlank()) {
                return Result.failure(Exception("Email cannot be empty"))
            }

            if (password.isBlank()) {
                return Result.failure(Exception("Password cannot be empty"))
            }

            // Get tutor by email
            val tutor = tutorRepository.getTutorByEmail(email)

            if (tutor != null) {
                // Verify password
                val passwordHash = hashPassword(password)
                if (tutor.passwordHash == passwordHash) {
                    Result.success(tutor)
                } else {
                    Result.failure(Exception("Invalid email or password"))
                }
            } else {
                Result.failure(Exception("Invalid email or password"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}

