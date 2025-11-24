package com.camila.pet_project.data.repositories

import com.camila.pet_project.data.dao.PetDao
import com.camila.pet_project.data.entity.PetEntity
import com.camila.pet_project.domain.model.PetDomain
import com.camila.pet_project.domain.repository.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PetRepositoryImpl @Inject constructor(
    private val petDao: PetDao
) : PetRepository {

    override suspend fun insertPet(pet: PetDomain): Long {
        return petDao.insertPet(pet.toEntity())
    }

    override suspend fun updatePet(pet: PetDomain) {
        petDao.updatePet(pet.toEntity())
    }

    override suspend fun deletePet(pet: PetDomain) {
        petDao.deletePet(pet.toEntity())
    }

    override suspend fun getPetById(petId: Int): PetDomain? {
        return petDao.getPetById(petId)?.toDomain()
    }

    override fun getPetsByUserId(userId: Int): Flow<List<PetDomain>> {
        return petDao.getPetsByUserId(userId).map { pets ->
            pets.map { it.toDomain() }
        }
    }

    override fun getAllPets(): Flow<List<PetDomain>> {
        return petDao.getAllPets().map { pets ->
            pets.map { it.toDomain() }
        }
    }

    private fun PetDomain.toEntity(): PetEntity {
        return PetEntity(
            id = id,
            name = name,
            type = type,
            breed = breed,
            age = age,
            photoUri = photoUri,
            address = address,
            tutorPhone = tutorPhone,
            fivFelvStatus = fivFelvStatus,
            description = description,
            userId = userId
        )
    }

    private fun PetEntity.toDomain(): PetDomain {
        return PetDomain(
            id = id,
            name = name,
            type = type,
            breed = breed,
            age = age,
            photoUri = photoUri,
            address = address,
            tutorPhone = tutorPhone,
            fivFelvStatus = fivFelvStatus,
            description = description,
            userId = userId
        )
    }
}

