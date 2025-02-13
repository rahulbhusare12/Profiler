package com.capgemini.profiler.domain

import androidx.activity.ComponentActivity
import com.capgemini.profiler.data.AuthRepository
import com.capgemini.profiler.ui.auth.AuthState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun sendOtp(phoneNumber: String, activity: ComponentActivity): Flow<AuthState> {
        return repository.sendOtp(phoneNumber, activity)
    }

    suspend fun verifyOtp(otp: String): Flow<AuthState> {
        return repository.verifyOtp(otp)
    }
}