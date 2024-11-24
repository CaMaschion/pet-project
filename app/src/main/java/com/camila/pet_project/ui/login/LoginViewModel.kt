package com.camila.pet_project.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camila.pet_project.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private fun login(userName: String, password: String) {
        viewModelScope.launch {
            val userExist = checkIfUserNameExist(userName)
            if (userExist) {
                val combinationExist =
                    validateUserAndPasswordCombination(userName, password)
                if (combinationExist) {
                    _loginState.update { LoginState.Success }
                } else {
                    _loginState.update { LoginState.Invalid }
                }
            } else {
                createUser(userName, password)
                _loginState.update { LoginState.Success }
            }
        }
    }

    private fun createUser(userName: String, password: String) {
        viewModelScope.launch {
            repository.insertUser(userName, password)
        }
    }

    private suspend fun validateUserAndPasswordCombination(
        userName: String,
        password: String
    ): Boolean {
        val userByUserNameAndPassword =
            repository.getUserByUserNameAndPassword(userName, password).firstOrNull()
        return userByUserNameAndPassword != null
    }

    private suspend fun checkIfUserNameExist(userName: String): Boolean {
        val user = repository.getUserByUserName(userName).firstOrNull()
        return user != null
    }
}

open class LoginState {
    object Idle : LoginState()
    object Success : LoginState()
    object Invalid : LoginState()
    object Loading : LoginState()
    data class Error(val message: String) : LoginState()
}
