package com.camila.pet_project.data.entity


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pet_table",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class PetEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val name: String = "",
    val type: String = "",
    val breed: String = "",
    val age: Int = 0,
    val description: String = "",
    val userId: Int = 0
)
