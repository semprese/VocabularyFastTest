package com.bignerdranch.android.voctest.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.voctest.AppViewModelProvider
import com.bignerdranch.android.voctest.data.MainRepository
import com.bignerdranch.android.voctest.data.VocDatabase

@Composable
fun HomeScreen(
    viewModel: SharedViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onPlay: () -> Unit,
    windowWidth: WindowWidthSizeClass,
) {
//    val uiState = viewModel.UiState.collectAsState()
    Scaffold { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    "Run test",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp)

                )
                Button(
                    onClick = onPlay,

                ) {
                    Icon(Icons.Default.PlayArrow, "", Modifier.size(100.dp))
                }
            }
        }

    }
}

@Preview
@Composable
fun PrevHomeScreen() {
    HomeScreen(
        viewModel = SharedViewModel(
            MainRepository(
                VocDatabase.getDatabase(LocalContext.current).wordDao()
            )
        ),
        onPlay = {},
        windowWidth = WindowWidthSizeClass.Compact
    )
}