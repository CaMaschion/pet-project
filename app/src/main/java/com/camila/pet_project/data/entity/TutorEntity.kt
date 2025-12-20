package com.camila.pet_project.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.camila.pet_project.domain.model.Tutor

@Entity(
    tableName = "tutors",
    indices = [Index(value = ["email"], unique = true)]
)
data class TutorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val passwordHash: String
)

fun TutorEntity.toDomain(): Tutor {
    return Tutor(
        id = id,
        name = name,
        email = email,
        passwordHash = passwordHash
    )
}

fun Tutor.toEntity(): TutorEntity {
    return TutorEntity(
        id = id,
        name = name,
        email = email,
        passwordHash = passwordHash
    )
}

