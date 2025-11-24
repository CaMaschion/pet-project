package com.camila.pet_project.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.camila.pet_project.data.entity.VaccineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccineDao {

    @Query("SELECT * FROM vaccine_table ORDER BY id ASC")
    fun getAllVaccines(): Flow<List<VaccineEntity>>

    @Query("SELECT * FROM vaccine_table WHERE id = :vaccineId")
    fun getVaccineById(vaccineId: Int): Flow<VaccineEntity>

}
