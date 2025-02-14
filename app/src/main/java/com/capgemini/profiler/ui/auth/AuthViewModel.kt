package com.capgemini.profiler.ui.auth

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capgemini.profiler.data.UserPreferences
import com.capgemini.profiler.domain.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    fun sendOtp(phoneNumber: String, activity: ComponentActivity) {
        viewModelScope.launch {
            authUseCase.sendOtp(phoneNumber, activity).collect { state ->
                _uiState.value = state
            }
        }
    }

    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            authUseCase.verifyOtp(otp).collect { state ->
                _uiState.value = state
            }
        }
    }
    fun checkAdminCredentials(userName: String, passWord:String) {
        viewModelScope.launch {
            authUseCase.verifyCredit(userName,passWord).collect { state ->
                _uiState.value = state
                if (state is AuthState.Success) {
                    userPreferences.saveLoginState(true, userName, passWord)
                }

            }
        }
    }
}
