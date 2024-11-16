package com.camila.pet_project.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.camila.pet_project.data.dao.PetDao
import com.camila.pet_project.data.dao.UserDao
import com.camila.pet_project.data.dao.VaccineDao
import com.camila.pet_project.data.dao.VaccineRegisterDao
import com.camila.pet_project.data.model.Pet
import com.camila.pet_project.data.model.User
import com.camila.pet_project.data.model.Vaccine
import com.camila.pet_project.data.model.VaccineRegister

@Database(
    entities = [
        Pet::class,
        User::class,
        Vaccine::class,
        VaccineRegister::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PetPassportDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun userDao(): UserDao
    abstract fun vaccineDao(): VaccineDao
    abstract fun vaccineRegisterDao(): VaccineRegisterDao
}