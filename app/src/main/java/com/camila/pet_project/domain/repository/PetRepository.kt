package com.camila.pet_project.domain.repository

import com.camila.pet_project.domain.model.PetDomain
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    suspend fun insertPet(pet: PetDomain): Long
    suspend fun updatePet(pet: PetDomain)
    suspend fun deletePet(pet: PetDomain)
    suspend fun getPetById(petId: Int): PetDomain?
    fun getPetsByTutorId(tutorId: Int): Flow<List<PetDomain>>
    fun getAllPets(): Flow<List<PetDomain>>
}
