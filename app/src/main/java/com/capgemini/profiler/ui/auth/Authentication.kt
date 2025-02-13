package com.capgemini.profiler.ui.auth

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@SuppressLint("ContextCastToActivity")
@Composable
fun AuthenticationScreen(
    userType: String,
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current as ComponentActivity

    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is AuthState.Loading -> {
                CircularProgressIndicator()
            }

            is AuthState.Idle -> PhoneInputSection(
                phoneNumber = phoneNumber,
                onPhoneChange = { phoneNumber = it },
                onSendOtp = { viewModel.sendOtp(phoneNumber, context) },
                isLoading = uiState is AuthState.Loading
            )

            is AuthState.OtpSent -> OtpInputSection(
                otp = otp,
                onOtpChange = { otp = it },
                onVerifyOtp = { viewModel.verifyOtp(otp) }
            )

            is AuthState.Success ->  SuccessMessage(message = "Login Success")

            is AuthState.Error -> ErrorMessage((uiState as AuthState.Error).message)
        }
    }
}

@Composable
fun PhoneInputSection(
    phoneNumber: String,
    onPhoneChange: (String) -> Unit,
    onSendOtp: () -> Unit,
    isLoading: Boolean
) {
    OutlinedTextField(
        value = phoneNumber,
        onValueChange = onPhoneChange,
        label = { Text("Enter Phone Number") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = onSendOtp, enabled = !isLoading) {
        Text("Send OTP")
    }
}

@Composable
fun OtpInputSection(
    otp: String,
    onOtpChange: (String) -> Unit,
    onVerifyOtp: () -> Unit
) {
    OutlinedTextField(
        value = otp,
        onValueChange = onOtpChange,
        label = { Text("Enter OTP") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = onVerifyOtp) {
        Text("Verify OTP")
    }
}

@Composable
fun ErrorMessage(message: String) {
    Text(text = message, color = Color.Red)
}

@Composable
fun SuccessMessage(message: String) {
    Text(text = message, color = Color.Red)
}