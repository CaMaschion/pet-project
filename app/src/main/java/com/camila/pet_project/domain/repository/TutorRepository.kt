package com.camila.pet_project.domain.repository

import com.camila.pet_project.domain.model.Tutor

interface TutorRepository {
    suspend fun registerTutor(tutor: Tutor): Result<Long>
    suspend fun isEmailRegistered(email: String): Boolean
    suspend fun getTutorByEmail(email: String): Tutor?
}

