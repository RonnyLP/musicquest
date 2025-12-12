package com.example.melodyquest.core.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.melodyquest.feature.home.ui.HomeScreen
import com.example.melodyquest.feature.login.ui.FullLoginScreen
import com.example.melodyquest.feature.login.ui.PasswordLoginScreen
import com.example.melodyquest.feature.login.ui.RegisterScreen
import com.example.melodyquest.feature.login.ui.SelectAccountScreen
import com.example.melodyquest.feature.login.ui.WelcomeScreen
import com.example.melodyquest.feature.trackplayer.ui.TrackPlayerScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Screen.Welcome.route
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToSelectAccount = {
                    navController.navigate(Screen.SelectAccount.route)
                }
            )
        }

        // Pantalla de selección de cuenta
        composable(Screen.SelectAccount.route) {
            SelectAccountScreen(
                onNavigateToPasswordLogin = { username ->
                    navController.navigate(Screen.PasswordLogin.createRoute(username))
                },
                onNavigateToFullLogin = {
                    navController.navigate(Screen.FullLogin.route)
                }
            )
        }

        // Pantalla de login con solo contraseña
        composable(
            route = Screen.PasswordLogin.route,
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""

            PasswordLoginScreen(
                username = username,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onForgotPassword = {
                    // TODO: Implementar recuperación de contraseña
                }
            )
        }

        // Pantalla de login completo
        composable(Screen.FullLogin.route) {
            FullLoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onForgotPassword = {
                    // TODO: Implementar recuperación de contraseña
                }
            )
        }

        // Pantalla de registro
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla principal (Home)
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToPlayer = { id ->
                    navController.navigate(Screen.Player.createRoute(id))
                }
            )
        }

        // Pantalla del reproductor
        composable(
            Screen.Player.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("id") ?: ""

            TrackPlayerScreen(
                trackId = id,
                onNavigateBack = {
//                    val current = navController.currentDestination?.route
//                    Log.d("NavTest", "Current: $current")
//
//                    val result = navController.popBackStack()
//                    Log.d("NavTest", "popBackStack: $result")

                    navController.navigate(Screen.Home.route)

//                    println("Se navegó hacia atrás")
                }
            )
        }

    }

}