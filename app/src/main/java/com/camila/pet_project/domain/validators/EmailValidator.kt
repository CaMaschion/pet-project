package com.camila.pet_project.domain.validators

import android.util.Patterns
import javax.inject.Inject

class EmailValidator @Inject constructor() {
    fun validate(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult.Error("Email cannot be empty")
        }
        // Note: Patterns.EMAIL_ADDRESS is an Android dependency.
        // Ideally, we should use a regex here to keep domain pure Kotlin,
        // or inject a validator interface implemented in data/app layer.
        // For simplicity in this refactor, I'll use a simple regex.
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        if (!emailRegex.matches(email)) {
             return ValidationResult.Error("Invalid email format")
        }
        return ValidationResult.Success
    }
}

