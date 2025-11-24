package com.camila.pet_project.ui.petlistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camila.pet_project.domain.model.PetDomain
import com.camila.pet_project.domain.usecase.GetPetsByUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PetListUiState(
    val pets: List<PetDomain> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PetListViewModel @Inject constructor(
    private val getPetsByUserIdUseCase: GetPetsByUserIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetListUiState())
    val uiState: StateFlow<PetListUiState> = _uiState.asStateFlow()

    fun loadPets(userId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                getPetsByUserIdUseCase(userId).collect { pets ->
                    _uiState.value = _uiState.value.copy(
                        pets = pets,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}