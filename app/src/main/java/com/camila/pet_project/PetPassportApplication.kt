package com.camila.pet_project

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PetPassportApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Application created")
    }

    companion object {
        const val TAG = "PetPassportApplication"
    }

}
