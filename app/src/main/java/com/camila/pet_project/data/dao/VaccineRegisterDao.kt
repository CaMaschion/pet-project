package com.camila.pet_project.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.camila.pet_project.data.entity.VaccineRegisterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccineRegisterDao {

    @Query("SELECT * FROM vaccine_register_table")
    fun getAllRegisterVaccines(): Flow<List<VaccineRegisterEntity>>

    @Query("SELECT * FROM vaccine_register_table WHERE id = :id")
    fun getRegisterVaccineById(id: Int): Flow<VaccineRegisterEntity>
    
}
