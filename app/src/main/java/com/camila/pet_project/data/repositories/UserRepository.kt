package com.camila.pet_project.data.repositories

import com.camila.pet_project.data.dao.UserDao
import com.camila.pet_project.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    fun getUserByUserName(userName: String): Flow<User> =
        userDao.getUserByUserName(userName)

    fun getUserByPassword(password: String): Flow<User> =
        userDao.getUserByPassword(password)

    fun getUserByUserNameAndPassword(userName: String, password: String): Flow<User> =
        userDao.getUserByUserNameAndPassword(userName, password)

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
}
