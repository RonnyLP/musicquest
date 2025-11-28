package com.example.melodyquest.core.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object SelectAccount : Screen("select_account")
    object PasswordLogin : Screen("password_login/{username}") {
        fun createRoute(username: String) = "password_login/$username"
    }
    object FullLogin : Screen("full_login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Player : Screen("player")
}