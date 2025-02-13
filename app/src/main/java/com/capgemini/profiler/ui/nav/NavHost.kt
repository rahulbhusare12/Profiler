package com.capgemini.profiler.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.capgemini.profiler.ui.auth.AuthenticationScreen
import com.capgemini.profiler.ui.loginOption.LoginOptionScreen
import com.capgemini.profiler.ui.splashScreen.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.LoginOption.route) { LoginOptionScreen(navController) }
        composable(Screen.Authentication.route) { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "user"
            AuthenticationScreen(userType,navController )
        }
    }
}
