package com.camila.pet_project.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.camila.pet_project.data.dao.PetDao
import com.camila.pet_project.data.dao.UserDao
import com.camila.pet_project.data.dao.VaccineDao
import com.camila.pet_project.data.dao.VaccineRegisterDao
import com.camila.pet_project.data.entity.PetEntity
import com.camila.pet_project.data.entity.UserEntity
import com.camila.pet_project.data.entity.VaccineEntity
import com.camila.pet_project.data.entity.VaccineRegisterEntity

@Database(
    entities = [
        PetEntity::class,
        UserEntity::class,
        VaccineEntity::class,
        VaccineRegisterEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class PetPassportDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun userDao(): UserDao
    abstract fun vaccineDao(): VaccineDao
    abstract fun vaccineRegisterDao(): VaccineRegisterDao
}
