package com.example.etherfi.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.signin.ui.screens.SignInScreen
import com.example.home.ui.screens.HomeScreen
import com.walletconnect.web3.modal.ui.web3ModalGraph

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object SignInScreen : Screen("sign_in_screen")
}

@Composable
fun AppNavigation(appPadding: PaddingValues, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "signInGraph") {
        navigation(startDestination = Screen.SignInScreen.route, route = "signInGraph") {
            composable(Screen.SignInScreen.route) {
                SignInScreen(
                    appPadding = appPadding,
                    navController = navController,
                    goToHomeScreen = {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo("signInGraph") { inclusive = true }
                        }
                    })
            }
            web3ModalGraph(navController)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(appPadding = appPadding, navController = navController)
        }
    }
}