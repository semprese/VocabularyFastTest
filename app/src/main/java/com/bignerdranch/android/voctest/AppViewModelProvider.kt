package com.bignerdranch.android.voctest

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bignerdranch.android.voctest.ui.ExamViewModel
import com.bignerdranch.android.voctest.ui.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            HomeViewModel(vocTestApplication().mainRepository)
        }


        initializer {
            ExamViewModel(vocTestApplication().mainRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [VocTestApplication].
 */
fun CreationExtras.vocTestApplication(): VocTestApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as VocTestApplication)