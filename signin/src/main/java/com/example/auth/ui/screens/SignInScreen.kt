package com.example.auth.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SignInScreen(appPadding: PaddingValues) {
    Text(modifier = Modifier.padding(paddingValues = appPadding), text = "SignInScreen")
}