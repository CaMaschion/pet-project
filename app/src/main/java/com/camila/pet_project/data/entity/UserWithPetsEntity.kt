package com.camila.pet_project.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithPetsEntity(
    @Embedded val user: UserEntity,
    @Relation(parentColumn = "id", entityColumn = "userId")
    val pets: List<PetEntity>
)