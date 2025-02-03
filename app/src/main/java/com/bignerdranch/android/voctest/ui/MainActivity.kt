package com.bignerdranch.android.voctest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.bignerdranch.android.voctest.VocTestApplication
import com.bignerdranch.android.voctest.ui.theme.VocTestTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val repo = (application as VocTestApplication).mainRepository
//        application.deleteDatabase("words_database")
        enableEdgeToEdge()
        setContent {
            VocTestTheme {
                val windowWidthSizeClassSize = calculateWindowSizeClass(this).widthSizeClass
                NavGraph(
                    windowWidthSizeClassSize
                )
            }
        }
    }
}

