package com.example.etherfi

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.etherfi.navigation.AppNavigation
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val modalSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
            val bottomSheetNavigator = BottomSheetNavigator(modalSheetState)
            val navController = rememberNavController(bottomSheetNavigator)
            ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
                AppNavigation(appPadding = PaddingValues(24.dp), navController = navController)
            }
        }

//        setContent {
//            val modalSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
//            val coroutineScope = rememberCoroutineScope()
//
//            ModalBottomSheetLayout(
//                sheetContent = {
//                    Web3ModalComponent(
//                        shouldOpenChooseNetwork = true,
//                        closeModal = { coroutineScope.launch { modalSheetState.hide() } }
//                    )
//                }
//            ) {
//                AppNavigation(appPadding = PaddingValues(24.dp))
//            }
//        }
    }
}