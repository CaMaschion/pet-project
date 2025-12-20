package com.camila.pet_project.data.repositories

import com.camila.pet_project.data.dao.TutorDao
import com.camila.pet_project.data.entity.toEntity
import com.camila.pet_project.data.entity.toDomain
import com.camila.pet_project.domain.model.Tutor
import com.camila.pet_project.domain.repository.TutorRepository
import javax.inject.Inject

class TutorRepositoryImpl @Inject constructor(
    private val tutorDao: TutorDao
) : TutorRepository {

    override suspend fun registerTutor(tutor: Tutor): Result<Long> {
        return try {
            val id = tutorDao.insertTutor(tutor.toEntity())
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return tutorDao.getTutorByEmail(email) != null
    }

    override suspend fun getTutorByEmail(email: String): Tutor? {
        return tutorDao.getTutorByEmail(email)?.toDomain()
    }
}

