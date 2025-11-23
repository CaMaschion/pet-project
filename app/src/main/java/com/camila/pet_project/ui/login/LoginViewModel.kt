package com.camila.pet_project.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camila.pet_project.domain.usecase.LoginUserUseCase
import com.camila.pet_project.domain.usecase.RegisterUserUseCase
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

/**
 * ViewModel for Login/Register screen
 * Following MVVM pattern and Single Responsibility Principle
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    /**
     * Handles login action
     */
    fun login() {
        val currentState = _uiState.value

        if (!currentState.isValid) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = loginUserUseCase(currentState.userName, currentState.password)

            result.fold(
                onSuccess = { user ->
                    Log.d(TAG, "Login successful for user: ${user.userName}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            currentUserId = user.id
                        )
                    }
                    _navigationEvents.emit(NavigationEvent.NavigateToPetList(user.id))
                },
                onFailure = { error ->
                    Log.e(TAG, "Login failed: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Login failed"
                        )
                    }
                }
            )
        }
    }

    /**
     * Handles register action
     */
    fun register() {
        val currentState = _uiState.value

        if (!currentState.isValid) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = registerUserUseCase(currentState.userName, currentState.password)

            result.fold(
                onSuccess = { user ->
                    Log.d(TAG, "Registration successful for user: ${user.userName}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            currentUserId = user.id
                        )
                    }
                    _navigationEvents.emit(NavigationEvent.NavigateToPetList(user.id))
                },
                onFailure = { error ->
                    Log.e(TAG, "Registration failed: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Registration failed"
                        )
                    }
                }
            )
        }
    }

    /**
     * Updates username field
     */
    fun updateUserName(userName: String) {
        _uiState.update {
            it.copy(
                userName = userName,
                errorMessage = null
            ).validate()
        }
    }

    /**
     * Updates password field
     */
    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                errorMessage = null
            ).validate()
        }
    }

    /**
     * Toggles between login and register mode
     */
    fun toggleMode() {
        _uiState.update {
            it.copy(
                isRegisterMode = !it.isRegisterMode,
                errorMessage = null
            )
        }
    }

    /**
     * Clears error message
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}

/**
 * UI State for Login screen
 */
data class LoginState(
    val userName: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegisterMode: Boolean = false,
    val isValid: Boolean = false,
    val currentUserId: Int = 0
) {
    /**
     * Validates the current state and updates isValid flag
     */
    fun validate(): LoginState {
        val valid = userName.isNotBlank() && password.isNotBlank()
        return this.copy(isValid = valid)
    }
}
