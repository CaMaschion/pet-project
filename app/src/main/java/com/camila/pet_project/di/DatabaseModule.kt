package com.camila.pet_project.di

import android.content.Context
import androidx.room.Room
import com.camila.pet_project.data.PetPassportDatabase
import com.camila.pet_project.data.dao.PetDao
import com.camila.pet_project.data.dao.TutorDao
import com.camila.pet_project.data.dao.VaccineDao
import com.camila.pet_project.data.dao.VaccineRegisterDao
import com.camila.pet_project.data.repositories.PetRepositoryImpl
import com.camila.pet_project.data.repositories.TutorRepositoryImpl
import com.camila.pet_project.domain.repository.PetRepository
import com.camila.pet_project.domain.repository.TutorRepository
import com.camila.pet_project.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PetPassportDatabase {
        return Room.databaseBuilder(
            context,
            PetPassportDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Singleton
    @Provides
    fun provideTutorDao(database: PetPassportDatabase): TutorDao = database.tutorDao()

    @Singleton
    @Provides
    fun providePetDao(database: PetPassportDatabase): PetDao = database.petDao()

    @Singleton
    @Provides
    fun provideVaccineDao(database: PetPassportDatabase): VaccineDao = database.vaccineDao()

    @Singleton
    @Provides
    fun provideVaccineRegisterDao(database: PetPassportDatabase): VaccineRegisterDao =
        database.vaccineRegisterDao()

    @Singleton
    @Provides
    fun provideTutorRepository(tutorRepositoryImpl: TutorRepositoryImpl): TutorRepository {
        return tutorRepositoryImpl
    }

    @Singleton
    @Provides
    fun providePetRepository(petRepositoryImpl: PetRepositoryImpl): PetRepository {
        return petRepositoryImpl
    }
}
