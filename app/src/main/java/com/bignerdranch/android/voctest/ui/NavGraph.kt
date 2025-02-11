package com.bignerdranch.android.voctest.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bignerdranch.android.voctest.AppViewModelProvider

enum class MainDestinations(val route: String) {
    HOME("home"),
    EXAM("exam")
}

@Composable
fun NavGraph(
    windowWidthSizeClassSize: WindowWidthSizeClass
) {
    val navController = rememberNavController()
    val viewModel: SharedViewModel = viewModel(
        factory = AppViewModelProvider.Factory
//        factory = SharedViewModel.provideFactory(
//            (application as VocTestApplication).mainRepository
//        )
    )
    NavHost(
        navController = navController,
        startDestination = MainDestinations.HOME.route
    ) {
        composable(MainDestinations.HOME.route) {
            HomeScreen(
                viewModel = viewModel,
                windowWidth = windowWidthSizeClassSize,
                onPlay = { navController.navigate(MainDestinations.EXAM.route)
                viewModel.loadWords(StageTest.FIRST)}
            )
        }
        composable(MainDestinations.EXAM.route) {
            ExamScreen(
                viewModel = viewModel,
                windowWidth = windowWidthSizeClassSize,
                onBackHome = { navController.navigateUp() }
            )
        }

    }
}