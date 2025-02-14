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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Idle -> PhoneInputSection(
                phoneNumber,
                { phoneNumber = it }) { viewModel.sendOtp(phoneNumber, context) }

            is AuthState.OtpSent -> OtpInputSection(otp, { otp = it }) { viewModel.verifyOtp(otp) }
            is AuthState.CheckCred -> CredentialInputSection(
                userName,
                password,
                { userName = it },
                { password = it }) { viewModel.checkAdminCredentials(userName, password) }

            is AuthState.Success -> SuccessMessage("Login Success")
            is AuthState.Error -> ErrorMessage((uiState as AuthState.Error).message)
        }
    }
}

@Composable
fun PhoneInputSection(phoneNumber: String, onPhoneChange: (String) -> Unit, onSendOtp: () -> Unit) {
    InputField(
        value = phoneNumber,
        onValueChange = onPhoneChange,
        label = "Enter Phone Number",
        keyboardType = KeyboardType.Phone
    )
    Spacer(modifier = Modifier.height(16.dp))
    ActionButton("Send OTP", onSendOtp)
}

@Composable
fun OtpInputSection(otp: String, onOtpChange: (String) -> Unit, onVerifyOtp: () -> Unit) {
    InputField(
        value = otp,
        onValueChange = onOtpChange,
        label = "Enter OTP",
        keyboardType = KeyboardType.Number
    )
    Spacer(modifier = Modifier.height(16.dp))
    ActionButton("Verify OTP", onVerifyOtp)
}

@Composable
fun CredentialInputSection(
    userName: String,
    password: String,
    onUserChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onLogin: () -> Unit
) {
    InputField(value = userName, onValueChange = onUserChange, label = "Enter Username")
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
        value = password,
        onValueChange = onPassChange,
        label = "Enter Password",
        keyboardType = KeyboardType.Password,
        isPassword = true
    )
    Spacer(modifier = Modifier.height(16.dp))
    ActionButton("Login", onLogin)
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) { Text(text) }
}

@Composable
fun ErrorMessage(message: String) {
    Text(text = message, color = Color.Red)
}

@Composable
fun SuccessMessage(message: String) {
    Text(text = message, color = Color.Green)
}
