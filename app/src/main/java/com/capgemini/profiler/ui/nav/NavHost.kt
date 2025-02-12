package com.capgemini.profiler.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.capgemini.profiler.ui.splashScreen.HomeScreen
import com.capgemini.profiler.ui.splashScreen.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
    }
}
