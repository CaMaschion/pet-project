package com.camila.pet_project.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.camila.pet_project.data.model.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {

    @Query("SELECT * FROM pet_table WHERE id = :petId")
    fun getPetById(petId: Int): Flow<Pet>

    @Query("SELECT * FROM pet_table WHERE userId = :userId")
    fun getPetByUserId(userId: Int): Flow<Pet>

    @Query("DELETE FROM pet_table WHERE id = :petId")
    suspend fun deletePetById(petId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPet(pet: Pet)

    @Update
    suspend fun updatePet(pet: Pet)

}
