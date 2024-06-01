package com.example.etherfi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import com.example.etherfi.navigation.AppNavigation
import com.example.etherfi.ui.theme.EtherfiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EtherfiTheme {
              Scaffold( topBar = {
                  TopAppBar(
                      title = {
                          Text("Etherfi")
                      }
                  )
              }) { paddingValues ->
                  AppNavigation(appPadding = paddingValues)
              }
            }
        }
    }
}