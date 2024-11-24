package com.camila.pet_project.data.repositories

import com.camila.pet_project.data.dao.UserDao
import com.camila.pet_project.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    fun getUserByUserName(userName: String): Flow<User> =
        userDao.getUserByUserName(userName)

    fun getUserByUserNameAndPassword(userName: String, password: String): Flow<User> =
        userDao.getUserByUserNameAndPassword(userName, password)

    fun insertUser(user: String, password: String) {
        userDao.insertUser(user, password)
    }
}
