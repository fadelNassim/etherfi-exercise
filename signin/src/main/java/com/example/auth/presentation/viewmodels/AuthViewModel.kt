package com.example.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val ioDispatcher: CoroutineDispatcher) : ViewModel() {

}