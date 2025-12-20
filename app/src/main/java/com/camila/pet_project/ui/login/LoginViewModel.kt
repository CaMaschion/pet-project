package com.camila.pet_project.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camila.pet_project.domain.usecase.LoginTutorUseCase
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
    private val loginTutorUseCase: LoginTutorUseCase
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

            val result = loginTutorUseCase(currentState.email, currentState.password)

            result.fold(
                onSuccess = { tutor ->
                    Log.d(TAG, "Login successful for tutor: ${tutor.name}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            currentUserId = tutor.id
                        )
                    }
                    _navigationEvents.emit(NavigationEvent.NavigateToPetList(tutor.id))
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
     * Navigates to register screen
     */
    fun navigateToRegister() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateToRegister)
        }
    }

    /**
     * Updates email field
     */
    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email,
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
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isValid: Boolean = false,
    val currentUserId: Int = 0
) {
    /**
     * Validates the current state and updates isValid flag
     */
    fun validate(): LoginState {
        val valid = email.isNotBlank() && password.isNotBlank()
        return this.copy(isValid = valid)
    }
}
