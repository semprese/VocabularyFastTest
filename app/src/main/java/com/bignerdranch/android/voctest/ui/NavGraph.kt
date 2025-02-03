package com.bignerdranch.android.voctest.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class MainDestinations(val route: String) {
    HOME("home"),
    EXAM("exam")
}

@Composable
fun NavGraph(
    windowWidthSizeClassSize: WindowWidthSizeClass
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainDestinations.HOME.route
    ) {
        composable(MainDestinations.HOME.route) {
            HomeScreen(
                windowWidth = windowWidthSizeClassSize,
                onPlay = { navController.navigate(MainDestinations.EXAM.route) }
            )
        }
        composable(MainDestinations.EXAM.route) {
            ExamScreen(
                windowWidth = windowWidthSizeClassSize,
                onBackHome = { navController.navigateUp() }
            )
        }

    }
}