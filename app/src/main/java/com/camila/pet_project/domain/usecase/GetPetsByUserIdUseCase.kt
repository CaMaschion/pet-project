package com.camila.pet_project.domain.usecase

import com.camila.pet_project.domain.model.PetDomain
import com.camila.pet_project.domain.repository.PetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPetsByUserIdUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    operator fun invoke(userId: Int): Flow<List<PetDomain>> {
        return petRepository.getPetsByUserId(userId)
    }
}

