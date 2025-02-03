package com.bignerdranch.android.voctest

import android.app.Application
import com.bignerdranch.android.voctest.data.MainRepository
import com.bignerdranch.android.voctest.data.VocDatabase

class VocTestApplication : Application() {
     lateinit var mainRepository: MainRepository
     override fun onCreate() {
        super.onCreate()
         mainRepository = MainRepository(VocDatabase.getDatabase(this).wordDao())

    }
}