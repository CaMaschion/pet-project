package com.camila.pet_project.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vaccine_register_table",
    foreignKeys = [
        ForeignKey(
            entity = VaccineEntity::class,
            parentColumns = ["id"],
            childColumns = ["vaccineId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PetEntity::class,
            parentColumns = ["id"],
            childColumns = ["petId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("vaccineId"), Index("petId")]
)
data class VaccineRegisterEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val vaccineId: Int,
    val petId: Int,
    val applicationDate: Long,
    val nextDueDate: Long? = null,
    val applicationAddress: String = "",
    val notes: String = ""
)