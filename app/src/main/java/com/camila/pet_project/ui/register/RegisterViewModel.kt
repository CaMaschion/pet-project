package com.camila.pet_project.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
 * ViewModel for Register screen
 * Following MVVM pattern and Single Responsibility Principle
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

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

            val result = registerUserUseCase(
                userName = currentState.userName,
                password = currentState.password,
                name = currentState.name,
                contactInfo = currentState.contactInfo,
                address = currentState.address
            )

            result.fold(
                onSuccess = { user ->
                    Log.d(TAG, "Registration successful for user: ${user.userName}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null
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
     * Updates name field
     */
    fun updateName(name: String) {
        _uiState.update {
            it.copy(
                name = name,
                errorMessage = null
            ).validate()
        }
    }

    /**
     * Updates contact info field
     */
    fun updateContactInfo(contactInfo: String) {
        _uiState.update {
            it.copy(
                contactInfo = contactInfo,
                errorMessage = null
            ).validate()
        }
    }

    /**
     * Updates address field
     */
    fun updateAddress(address: String) {
        _uiState.update {
            it.copy(
                address = address,
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
        private const val TAG = "RegisterViewModel"
    }
}

/**
 * UI State for Register screen
 */
data class RegisterState(
    val userName: String = "",
    val password: String = "",
    val name: String = "",
    val contactInfo: String = "",
    val address: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isValid: Boolean = false
) {
    /**
     * Validates the current state and updates isValid flag
     */
    fun validate(): RegisterState {
        val valid = userName.isNotBlank() &&
                    password.isNotBlank() &&
                    name.isNotBlank() &&
                    contactInfo.isNotBlank() &&
                    address.isNotBlank()
        return this.copy(isValid = valid)
    }
}

