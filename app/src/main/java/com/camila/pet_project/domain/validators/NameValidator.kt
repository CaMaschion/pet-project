package com.camila.pet_project.domain.validators

import javax.inject.Inject

class NameValidator @Inject constructor() {
    fun validate(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult.Error("Name cannot be empty")
        }
        if (name.length < 3) {
            return ValidationResult.Error("Name must be at least 3 characters")
        }
        if (!name.all { it.isLetter() || it.isWhitespace() }) {
            return ValidationResult.Error("Name must contain only letters and spaces")
        }
        return ValidationResult.Success
    }
}

