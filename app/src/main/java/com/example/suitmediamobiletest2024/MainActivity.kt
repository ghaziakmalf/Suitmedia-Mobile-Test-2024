package com.example.suitmediamobiletest2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.suitmediamobiletest2024.ui.navigation.NavigationGraph
import com.example.suitmediamobiletest2024.ui.theme.SuitmediaMobileTest2024Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SuitmediaMobileTest2024Theme {
                NavigationGraph(navController = navController)
            }
        }
    }
}