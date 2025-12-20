package com.camila.pet_project.presentation.tutor_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camila.pet_project.domain.usecase.RegisterResult
import com.camila.pet_project.domain.usecase.RegisterTutorUseCase
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
class TutorRegisterViewModel @Inject constructor(
    private val registerTutorUseCase: RegisterTutorUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TutorRegisterState())
    val uiState: StateFlow<TutorRegisterState> = _uiState.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword, confirmPasswordError = null) }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onToggleConfirmPasswordVisibility() {
        _uiState.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, generalError = null) }
            val state = _uiState.value

            val result = registerTutorUseCase(
                name = state.name,
                email = state.email,
                password = state.password,
                confirmPassword = state.confirmPassword
            )

            _uiState.update { it.copy(isLoading = false) }

            when (result) {
                is RegisterResult.Success -> {
                    _navigationEvents.emit(NavigationEvent.NavigateToPetList(result.tutorId.toInt()))
                }
                is RegisterResult.ValidationError -> {
                    _uiState.update {
                        it.copy(
                            nameError = result.nameError,
                            emailError = result.emailError,
                            passwordError = result.passwordError,
                            confirmPasswordError = result.confirmPasswordError
                        )
                    }
                }
                is RegisterResult.Error -> {
                    _uiState.update { it.copy(generalError = result.message) }
                }
            }
        }
    }
}

