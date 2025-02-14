package com.capgemini.profiler.ui.nav

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object LoginOption : Screen("login_option_screen")
    object Authentication : Screen("authentication_screen/{userType}") {
        fun passUserType(userType: String) = "authentication_screen/$userType"
    }
    object AdminDashBoard : Screen("admin_dash_board_screen")
}