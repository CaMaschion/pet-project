package com.camila.pet_project.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PetWithVaccineRegistersEntity(
    @Embedded val pet: PetEntity,
    @Relation(parentColumn = "id", entityColumn = "petId")
    val vaccineRegisterEntities: List<VaccineRegisterEntity>
)
