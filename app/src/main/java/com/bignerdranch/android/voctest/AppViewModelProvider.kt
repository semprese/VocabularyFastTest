package com.bignerdranch.android.voctest

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bignerdranch.android.voctest.ui.SharedViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

//        initializer {
//            HomeViewModel(vocTestApplication().mainRepository)
//        }


        initializer {
            SharedViewModel(vocTestApplication().mainRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [VocTestApplication].
 */
fun CreationExtras.vocTestApplication(): VocTestApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as VocTestApplication)