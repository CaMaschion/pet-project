package com.camila.pet_project.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camila.pet_project.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()


    fun login(userName: String, password: String) {
        viewModelScope.launch {
            val userExist = checkIfUserNameExist(userName)

            if (userExist) {
                val combinationExist =
                    validateUserAndPasswordCombination(userName, password)

                if (combinationExist) {
                    Log.i("LoginViewModel", "LoginState.Success")
                } else {
                    Log.i("LoginViewModel", "LoginState.Error")
                }

            } else {
                createUser(userName, password)
                Log.i("LoginViewModel", "LoginState.Success")
            }
        }
    }

    private suspend fun createUser(userName: String, password: String) {
        repository.insertUser(userName, password)
    }

    private suspend fun validateUserAndPasswordCombination(
        userName: String,
        password: String
    ): Boolean {
        repository.getUserByUserNameAndPassword(userName, password)
        return true
    }

    private suspend fun checkIfUserNameExist(userName: String): Boolean {
        val user = repository.getUserByUserName(userName)
        return user != null
    }

    fun updateUserName(userName: String) {
        _uiState.update {
            it.copy(userName = userName)
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }
}

data class LoginState(
    var userName: String = "",
    val password: String = ""
)
