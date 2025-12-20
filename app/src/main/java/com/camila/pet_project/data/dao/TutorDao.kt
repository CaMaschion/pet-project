package com.camila.pet_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camila.pet_project.data.entity.TutorEntity

@Dao
interface TutorDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTutor(tutor: TutorEntity): Long

    @Query("SELECT * FROM tutors WHERE email = :email")
    suspend fun getTutorByEmail(email: String): TutorEntity?
}

