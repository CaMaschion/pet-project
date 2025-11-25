package com.camila.pet_project.data.repositories

import com.camila.pet_project.data.dao.UserDao
import com.camila.pet_project.data.entity.UserEntity
import com.camila.pet_project.domain.model.UserDomain
import com.camila.pet_project.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of UserRepository
 * Handles data operations and mapping between data and domain layers
 * Following Repository Pattern and Single Responsibility Principle
 */
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUserByUserName(userName: String): UserDomain? {
        return userDao.getUserByUserName(userName)?.toDomain()
    }

    override suspend fun authenticateUser(userName: String, password: String): UserDomain? {
        return userDao.getUserByUserNameAndPassword(userName, password)?.toDomain()
    }

    override suspend fun registerUser(
        userName: String,
        password: String,
        name: String,
        contactInfo: String,
        address: String
    ): Result<UserDomain> {
        return try {
            val newUser = UserEntity(
                userName = userName,
                password = password,
                name = name,
                contactInfo = contactInfo,
                address = address
            )
            userDao.insertUser(newUser)

            // Retrieve the created user to get the generated ID
            val createdUser = userDao.getUserByUserName(userName)
            if (createdUser != null) {
                Result.success(createdUser.toDomain())
            } else {
                Result.failure(Exception("Failed to retrieve created user"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Failed to register user: ${e.message}"))
        }
    }

    /**
     * Extension function to map User entity to UserDomain
     */
    private fun UserEntity.toDomain(): UserDomain {
        return UserDomain(
            id = this.id,
            userName = this.userName,
            password = this.password,
            name = this.name,
            contactInfo = this.contactInfo,
            address = this.address
        )
    }
}
