package com.camila.pet_project.domain.usecase

import com.camila.pet_project.domain.model.PetDomain
import com.camila.pet_project.domain.repository.PetRepository
import javax.inject.Inject

class DeletePetUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(pet: PetDomain) {
        petRepository.deletePet(pet)
    }
}

