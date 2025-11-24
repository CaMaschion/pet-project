package com.camila.pet_project.ui.addpetscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camila.pet_project.domain.model.PetDomain
import com.camila.pet_project.domain.usecase.AddPetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddPetUiState(
    val name: String = "",
    val breed: String = "",
    val age: String = "",
    val address: String = "",
    val tutorPhone: String = "",
    val fivFelvStatus: String = "not_tested",
    val photoUri: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

@HiltViewModel
class AddPetViewModel @Inject constructor(
    private val addPetUseCase: AddPetUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddPetUiState())
    val uiState: StateFlow<AddPetUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onBreedChange(breed: String) {
        _uiState.value = _uiState.value.copy(breed = breed)
    }

    fun onAgeChange(age: String) {
        _uiState.value = _uiState.value.copy(age = age)
    }

    fun onAddressChange(address: String) {
        _uiState.value = _uiState.value.copy(address = address)
    }

    fun onTutorPhoneChange(phone: String) {
        _uiState.value = _uiState.value.copy(tutorPhone = phone)
    }

    fun onFivFelvStatusChange(status: String) {
        _uiState.value = _uiState.value.copy(fivFelvStatus = status)
    }

    fun onPhotoUriChange(uri: String) {
        _uiState.value = _uiState.value.copy(photoUri = uri)
    }

    fun savePet(userId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Validation
            val currentState = _uiState.value
            if (currentState.name.isBlank()) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "Nome é obrigatório"
                )
                return@launch
            }

            val age = currentState.age.toIntOrNull()
            if (age == null || age < 0) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "Idade inválida"
                )
                return@launch
            }

            try {
                val pet = PetDomain(
                    name = currentState.name,
                    breed = currentState.breed,
                    age = age,
                    address = currentState.address,
                    tutorPhone = currentState.tutorPhone,
                    fivFelvStatus = currentState.fivFelvStatus,
                    photoUri = currentState.photoUri,
                    userId = userId,
                    type = "cat" // Default to cat for now
                )

                addPetUseCase(pet)
                _uiState.value = currentState.copy(
                    isLoading = false,
                    success = true,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = e.message ?: "Erro ao salvar pet"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = AddPetUiState()
    }
}
