package com.camila.pet_project.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.camila.pet_project.data.dao.PetDao
import com.camila.pet_project.data.dao.TutorDao
import com.camila.pet_project.data.dao.VaccineDao
import com.camila.pet_project.data.dao.VaccineRegisterDao
import com.camila.pet_project.data.entity.PetEntity
import com.camila.pet_project.data.entity.TutorEntity
import com.camila.pet_project.data.entity.VaccineEntity
import com.camila.pet_project.data.entity.VaccineRegisterEntity

@Database(
    entities = [
        PetEntity::class,
        TutorEntity::class,
        VaccineEntity::class,
        VaccineRegisterEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class PetPassportDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun tutorDao(): TutorDao
    abstract fun vaccineDao(): VaccineDao
    abstract fun vaccineRegisterDao(): VaccineRegisterDao
}
