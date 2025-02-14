package com.capgemini.profiler.ui.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capgemini.profiler.data.UserPreferences
import com.capgemini.profiler.ui.nav.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SplashViewModel @Inject constructor(
    private val userPreferences: UserPreferences
): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _navigateTo = MutableStateFlow<String?>(null)
    val navigateTo: StateFlow<String?> = _navigateTo

    init {
        viewModelScope.launch {
            delay(2000)
            val isLoggedIn = userPreferences.loginStateFlow.first()
            _navigateTo.value = when {
                isLoggedIn  -> Screen.AdminDashBoard.route
                else -> Screen.Authentication.route
            }
            _isLoading.value = false
        }
    }
}