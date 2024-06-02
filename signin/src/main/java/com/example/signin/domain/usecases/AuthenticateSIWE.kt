package com.example.signin.domain.usecases

import com.example.signin.data.models.SiweResponse
import com.example.signin.data.repositories.SiweRepository
import com.example.signin.domain.entities.SiweResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthenticateSIWE @Inject constructor(private val repository: SiweRepository) {
     fun invoke(): Flow<SiweResult> {
         repository.authenticate()
        return repository.result.map {
            when (it) {
                is SiweResponse.Success -> SiweResult.Success(message = it.message)
                is SiweResponse.Error -> SiweResult.Error(message = it.message)
            }
        }
     }
}