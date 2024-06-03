package com.example.signin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.signin.R
import com.example.signin.domain.entities.ErrorReason

@Composable
fun TryAgainScreen(errorReason: ErrorReason, onClick: () -> Unit) {
    Column {
        when (errorReason) {
            ErrorReason.FailedSiweAuthenticate -> {
                Text(text = stringResource(id = R.string.session_siwe_auth_error))
            }

            else -> {}
        }
        Button(onClick = { onClick() }) {
            Text(text = stringResource(id = R.string.try_again_button))
        }
    }
}

@Preview
@Composable
fun TryAgainScreenPreview() {
    TryAgainScreen(ErrorReason.FailedSiweAuthenticate, {})
}