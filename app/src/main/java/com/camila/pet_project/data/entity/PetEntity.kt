package com.camila.pet_project.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pet_table",
    foreignKeys = [
        ForeignKey(
            entity = TutorEntity::class,
            parentColumns = ["id"],
            childColumns = ["tutorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("tutorId")]
)
data class PetEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val name: String = "",
    val type: String = "",
    val breed: String = "",
    val age: Int = 0,
    val photoUri: String = "",
    val address: String = "",
    val tutorPhone: String = "",
    val fivFelvStatus: String = "", // "negative", "positive", or "not_tested"
    val description: String = "",
    val tutorId: Int = 0
)
