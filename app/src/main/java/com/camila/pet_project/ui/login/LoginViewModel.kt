package com.camila.pet_project.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camila.pet_project.data.repositories.UserRepositoryImpl
import com.camila.pet_project.ui.navigation.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            val userExist = checkIfUserNameExist(userName)
            if (userExist) {
                val combinationExist =
                    validateUserAndPasswordCombination(userName, password)
                if (combinationExist) {
                    _navigationEvents.emit(NavigationEvent.NavigateToPetList)
                } else {
                    Log.i("LoginViewModel", "LoginState.Error")
                }

            } else {
                createUser(userName, password)
                _navigationEvents.emit(NavigationEvent.NavigateToPetList)
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
        val user = repository.getUserByUserNameAndPassword(userName, password)
        return user != null
    }

    private suspend fun checkIfUserNameExist(userName: String): Boolean {
        val user = repository.getUserByUserName(userName)
        return user != null
    }

    fun updateUserName(userName: String) {
        _uiState.update {
            it.copy(
                userName = userName,
                readyToLogin = isReadyToLogin(userName, it.password)
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                readyToLogin = isReadyToLogin(it.userName, password)
            )
        }
    }

    private fun isReadyToLogin(
        userName: String,
        password: String
    ): Boolean {
        return userName.isNotEmpty() && password.isNotEmpty()
    }
}

data class LoginState(
    var userName: String = "",
    val password: String = "",
    val readyToLogin: Boolean = false
)
