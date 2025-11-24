package com.camila.pet_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.camila.pet_project.data.entity.PetEntity
import com.camila.pet_project.data.entity.PetWithVaccineRegistersEntity
import com.camila.pet_project.data.entity.UserEntity
import com.camila.pet_project.data.entity.UserWithPetsEntity
import com.camila.pet_project.data.entity.VaccineEntity
import com.camila.pet_project.data.entity.VaccineRegisterEntity

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccine(vaccine: VaccineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccineRegister(register: VaccineRegisterEntity): Long

    @Transaction
    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getUserWithPets(userId: Int): UserWithPetsEntity?

    @Transaction
    @Query("SELECT * FROM pet_table WHERE id = :petId")
    suspend fun getPetWithVaccineRegisters(petId: Int): PetWithVaccineRegistersEntity?

    @Transaction
    @Query("SELECT * FROM vaccine_register_table WHERE nextDueDate IS NOT NULL AND nextDueDate <= :now")
    suspend fun getDueVaccineRegisters(now: Long): List<VaccineRegisterEntity>
}
