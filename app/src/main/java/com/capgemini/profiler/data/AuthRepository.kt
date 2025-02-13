package com.capgemini.profiler.data

import androidx.activity.ComponentActivity
import com.capgemini.profiler.ui.auth.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun sendOtp(phoneNumber: String, activity: ComponentActivity): Flow<AuthState>
    suspend fun verifyOtp(otp: String): Flow<AuthState>
}