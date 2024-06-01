package com.example.etherfi.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auth.ui.screens.SignInScreen
import com.example.home.ui.screens.HomeScreen

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object SignInScreen : Screen("sign_in_screen")
}

@Composable
fun AppNavigation(appPadding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(appPadding)
        }
        composable(Screen.SignInScreen.route) {
            SignInScreen(appPadding)
        }
    }
}