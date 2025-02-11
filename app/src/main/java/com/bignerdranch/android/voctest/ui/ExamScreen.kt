package com.bignerdranch.android.voctest.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.voctest.AppViewModelProvider
import com.bignerdranch.android.voctest.R
import com.bignerdranch.android.voctest.data.MainRepository
import com.bignerdranch.android.voctest.data.VocDatabase
import com.bignerdranch.android.voctest.model.ExamWordState
import com.bignerdranch.android.voctest.model.Word

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    viewModel: SharedViewModel = viewModel(factory = AppViewModelProvider.Factory),
    windowWidth: WindowWidthSizeClass,
    onBackHome: () -> Unit,
) {
    val uiState = viewModel.UiState.collectAsStateWithLifecycle()
    val currentStageTest = uiState.value.stage


    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if (currentStageTest == StageTest.FIRST) "Step 1 of 2"
                        else "Step 2 of 2"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackHome) {
                        Icon(Icons.Default.Close, "Close")
                    }
                }
            )
        }
    ) { innerPadding ->
        val countOfColumn = when (windowWidth) {
            WindowWidthSizeClass.Medium -> 2
            WindowWidthSizeClass.Expanded -> 3
            else -> 2
        }

        if (uiState.value.isLoading)
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.width(64.dp))
            }
        else
            when (currentStageTest) {
                StageTest.FIRST -> ExamScreen(
                    countOfColumn = countOfColumn,
                    wordList = uiState.value.listWords,
                    onNextClick = { viewModel.loadWords(StageTest.SECOND) },
                    modifier = Modifier.padding(innerPadding)
                )

                StageTest.SECOND -> ExamScreen(
                    countOfColumn = countOfColumn,
                    wordList = uiState.value.listWords,
                    onNextClick = { viewModel.loadWords(StageTest.END) },
                    modifier = Modifier.padding(innerPadding)
                )

                StageTest.END -> ResultStateScreen(
                    onDone = onBackHome,
                    modifier = Modifier.padding(innerPadding)
                )
            }
    }
}

@Composable
fun ExamScreen(
    countOfColumn: Int,
    wordList: List<Word>,
    onNextClick: () -> Unit,
    modifier: Modifier,
) {
    val wordStateList = wordList.map {
        ExamWordState(
            it,
            rememberSaveable { mutableStateOf(false) })
    }
    Column(
        modifier = modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(countOfColumn),
//            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(countOfColumn) }) {
                Text(
                    text = stringResource(R.string.help_of_top_screen),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
            itemsIndexed(wordStateList) { index, wordState ->
                fun changeState() {
                    wordStateList[index].state.value = !wordStateList[index].state.value
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .clickable {
                            changeState()
                        }) {
                    Checkbox(wordStateList[index].state.value, {
                        changeState()
                    })
                    Text(
                        wordState.word.name,
                    )
                }
            }
            item(span = { GridItemSpan(countOfColumn) }) {
                Button(
                    onClick = onNextClick,
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color.Red
                    ),
                    shape = ShapeDefaults.Small,
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier.padding(horizontal = 80.dp)
                ) {
                    Text("Continue")
                }
            }
        }
    }

}

@Composable
fun ResultStateScreen(modifier: Modifier, onDone: () -> Unit) {

}

@Preview
@Composable
fun PrevExamScreen() {
    ExamScreen(
        onBackHome = { {} },
        windowWidth = WindowWidthSizeClass.Compact,
        viewModel = SharedViewModel(
            MainRepository(
                VocDatabase.getDatabase(
                    LocalContext.current
                ).wordDao()
            )
        ),

        )
}