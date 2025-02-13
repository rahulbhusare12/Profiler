package com.capgemini.profiler.ui.loginOption

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.capgemini.profiler.R.string
import com.capgemini.profiler.ui.nav.Screen

@Composable
fun LoginOptionScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        LoginOptionScreenUi(
            onAdminLogin = {
                navController.navigate(Screen.Authentication.passUserType("Admin"))
            },
            onUserLogin = {
                navController.navigate(Screen.Authentication.passUserType("User"))
            }

        )
    }
}

@Composable
fun LoginOptionScreenUi(
    onAdminLogin: () -> Unit,
    onUserLogin: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = onAdminLogin) {
                Text(text = stringResource(string.btn_admin_login_label))
            }
            Button(onClick = onUserLogin) {
                Text(text = stringResource(string.btn_user_login_label))
            }
        }
    }
}
