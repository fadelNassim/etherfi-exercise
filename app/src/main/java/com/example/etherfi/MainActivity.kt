package com.example.etherfi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.etherfi.navigation.AppNavigation
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.tinder.scarlet.Message
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val modalSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true
            )
            val bottomSheetNavigator = BottomSheetNavigator(modalSheetState)
            val navController = rememberNavController(bottomSheetNavigator)
            Scaffold(
                topBar = { TopAppBar(title = { Message.Text("Etherfi") }) },
            ) { appPadding ->
                ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
                    AppNavigation(appPadding = appPadding, navController = navController)
                }
            }
        }
    }
}