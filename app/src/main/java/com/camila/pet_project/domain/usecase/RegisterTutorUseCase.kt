package com.camila.pet_project.domain.usecase

import com.camila.pet_project.domain.model.Tutor
import com.camila.pet_project.domain.repository.TutorRepository
import com.camila.pet_project.domain.validators.EmailValidator
import com.camila.pet_project.domain.validators.NameValidator
import com.camila.pet_project.domain.validators.PasswordValidator
import com.camila.pet_project.domain.validators.ValidationResult
import java.security.MessageDigest
import javax.inject.Inject

class RegisterTutorUseCase @Inject constructor(
    private val tutorRepository: TutorRepository,
    private val nameValidator: NameValidator,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): RegisterResult {
        val nameResult = nameValidator.validate(name)
        if (nameResult is ValidationResult.Error) {
            return RegisterResult.ValidationError(nameError = nameResult.message)
        }

        val emailResult = emailValidator.validate(email)
        if (emailResult is ValidationResult.Error) {
            return RegisterResult.ValidationError(emailError = emailResult.message)
        }

        val passwordResult = passwordValidator.validate(password)
        if (passwordResult is ValidationResult.Error) {
            return RegisterResult.ValidationError(passwordError = passwordResult.message)
        }

        if (password != confirmPassword) {
            return RegisterResult.ValidationError(confirmPasswordError = "Passwords do not match")
        }

        if (tutorRepository.isEmailRegistered(email)) {
            return RegisterResult.ValidationError(emailError = "Email already registered")
        }

        val passwordHash = hashPassword(password)
        val tutor = Tutor(
            name = name,
            email = email,
            passwordHash = passwordHash
        )

        val result = tutorRepository.registerTutor(tutor)
        return if (result.isSuccess) {
            RegisterResult.Success(result.getOrThrow())
        } else {
            RegisterResult.Error(result.exceptionOrNull()?.message ?: "Unknown error")
        }
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}

sealed class RegisterResult {
    data class Success(val tutorId: Long) : RegisterResult()
    data class Error(val message: String) : RegisterResult()
    data class ValidationError(
        val nameError: String? = null,
        val emailError: String? = null,
        val passwordError: String? = null,
        val confirmPasswordError: String? = null
    ) : RegisterResult()
}

