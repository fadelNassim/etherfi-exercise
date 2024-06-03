package com.example.signin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.signin.R

@Composable
fun SiweScreen(
    onCancelClick: () -> Unit,
    onSiweClick: () -> Unit,
    onSiweWrongMessageClick: () -> Unit
) {
    Column {
        Button(onClick = { onCancelClick() }) {
            Text(text = stringResource(id = R.string.cancel_button))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onSiweClick() }) {
            Text(text = stringResource(id = R.string.siwe_button))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onSiweWrongMessageClick() }) {
            Text(text = stringResource(id = R.string.siwe_wrong_message_button))
        }
    }
}

@Preview
@Composable
fun SiweScreenPreview() {
    SiweScreen({}, {}, {})
}