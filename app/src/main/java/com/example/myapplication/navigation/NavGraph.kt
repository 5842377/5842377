package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.GraduationScreen
import com.example.myapplication.ui.screens.MessageScreen
import com.example.myapplication.ui.screens.QuoteScreen
import com.example.myapplication.viewmodel.AppViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: AppViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("quote") {
            QuoteScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("tortoiseshell") {
            GraduationScreen(navController)
        }
        composable("messages") {
            MessageScreen(
                appViewModel = viewModel,
                navController = navController
            )
        }
    }
}