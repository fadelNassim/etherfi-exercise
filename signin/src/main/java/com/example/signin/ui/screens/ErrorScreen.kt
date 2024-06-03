package com.example.signin.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.signin.R
import com.example.signin.domain.entities.ErrorReason

@Composable
fun ErrorScreen(errorReason: ErrorReason) {
    val errorMessage = when (errorReason) {
        is ErrorReason.GenericError -> {
            stringResource(id = R.string.generic_error)
        }

        is ErrorReason.SessionAuthenticateError -> {
            stringResource(id = R.string.session_auth_error)
        }

        is ErrorReason.ExpiredProposal -> {
            stringResource(id = R.string.expired_proposal)
        }

        is ErrorReason.DeletedSessionError -> {
            stringResource(id = R.string.deleted_session_error)
        }

        is ErrorReason.RejectedSession -> {
            stringResource(id = R.string.rejected_session)
        }

        is ErrorReason.ConnectionNotAvailable -> {
            stringResource(id = R.string.connection_not_available)
        }

        is ErrorReason.FailedSiweAuthenticate -> {
            stringResource(id = R.string.session_auth_error)
        }
    }
    Text(text = errorMessage)
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(ErrorReason.GenericError)
}