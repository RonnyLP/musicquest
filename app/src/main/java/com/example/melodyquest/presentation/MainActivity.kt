package com.example.melodyquest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.melodyquest.core.navigation.AppNavHost
import com.example.melodyquest.core.theme.MelodyQuestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            MelodyQuestTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//                AudioManipulationScreen()
//                SoundPoolScreen()
//                ChordPlayerDemoScreen()
//            }
            val navController = rememberNavController()
            AppNavHost(navController)
        }
    }
}