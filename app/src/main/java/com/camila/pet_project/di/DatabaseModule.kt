package com.camila.pet_project.di

import android.content.Context
import androidx.room.Room
import com.camila.pet_project.data.PetPassportDatabase
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
    ) = Room.databaseBuilder(
        context,
        PetPassportDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: PetPassportDatabase) {
        database.userDao()
        database.petDao()
        database.vaccineDao()
        database.vaccineRegisterDao()
    }
}
