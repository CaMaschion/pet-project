package com.camila.pet_project.data.repositories

import com.camila.pet_project.data.dao.UserDao
import com.camila.pet_project.data.model.User
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class UserRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun getUserByUserName(userName: String): User =
        userDao.getUserByUserName(userName)

    suspend fun getUserByUserNameAndPassword(userName: String, password: String): User =
        userDao.getUserByUserNameAndPassword(userName, password)

    suspend fun insertUser(user: String, password: String) {
        val newUser = User(
            id = 1,
            userName = user,
            password = password
        )
        userDao.insertUser(newUser)
    }
}
