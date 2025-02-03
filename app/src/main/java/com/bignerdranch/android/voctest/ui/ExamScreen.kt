package com.bignerdranch.android.voctest.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.voctest.AppViewModelProvider
import com.bignerdranch.android.voctest.data.MainRepository
import com.bignerdranch.android.voctest.data.VocDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    viewModel: ExamViewModel = viewModel(factory = AppViewModelProvider.Factory),
    windowWidth: WindowWidthSizeClass,
    onBackHome: () -> Unit,
) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Exam...") },
                navigationIcon = { IconButton( onClick = onBackHome){
                    Icon(Icons.AutoMirrored.Default.ExitToApp, "Close") }
                }
            )
        }
    ){ innerPadding ->
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        val currentStageTest = uiState.value.stage
        val context = LocalContext.current
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ){
            Button(
                onClick = {
                    Toast.makeText(context, "BibA", Toast.LENGTH_LONG).show()
                }
            ) {
                Text("aboba")
            }
        }
    }
}

@Preview
@Composable
fun PrevExamScreen(){
    ExamScreen(
        onBackHome = { {} },
        windowWidth = WindowWidthSizeClass.Compact,
        viewModel = ExamViewModel(
            MainRepository(
                VocDatabase.getDatabase(
                    LocalContext.current).wordDao())
        ),

        )
}