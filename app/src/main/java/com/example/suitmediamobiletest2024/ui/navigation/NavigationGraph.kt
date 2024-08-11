package com.example.suitmediamobiletest2024.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.suitmediamobiletest2024.ui.screens.FirstScreen
import com.example.suitmediamobiletest2024.ui.screens.SecondScreen
import com.example.suitmediamobiletest2024.ui.screens.ThirdScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "first_screen"
    ) {
        composable("first_screen") {
            FirstScreen(navController = navController)
        }
        composable("second_screen/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val selectedUser = backStackEntry.savedStateHandle.get<String>("selected_user")
            SecondScreen(navController = navController, name = name, selectedUser = selectedUser)
        }
        composable("third_screen") {
            ThirdScreen(navController = navController) { selectedUser ->
                navController.previousBackStackEntry?.savedStateHandle?.set("selected_user", selectedUser)
            }
        }
    }
}
