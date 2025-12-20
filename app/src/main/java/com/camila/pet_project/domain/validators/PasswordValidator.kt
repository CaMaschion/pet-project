package com.camila.pet_project.domain.validators

import javax.inject.Inject

class PasswordValidator @Inject constructor() {
    fun validate(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult.Error("Password must be at least 8 characters")
        }
        if (!password.any { it.isUpperCase() }) {
            return ValidationResult.Error("Password must contain at least 1 uppercase letter")
        }
        if (!password.any { it.isLowerCase() }) {
            return ValidationResult.Error("Password must contain at least 1 lowercase letter")
        }
        if (!password.any { it.isDigit() }) {
            return ValidationResult.Error("Password must contain at least 1 number")
        }
        if (!password.any { !it.isLetterOrDigit() && !it.isWhitespace() }) {
            return ValidationResult.Error("Password must contain at least 1 special character")
        }
        if (password.any { it.isWhitespace() }) {
            return ValidationResult.Error("Password cannot contain spaces")
        }
        return ValidationResult.Success
    }
}

