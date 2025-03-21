package com.audita3077.assessment1.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{herbalName}") {
        fun createRoute(herbalName: String) = "detail/$herbalName"
    }
}
