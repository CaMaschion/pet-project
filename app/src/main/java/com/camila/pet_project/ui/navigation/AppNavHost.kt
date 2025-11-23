package com.camila.pet_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.camila.pet_project.ui.login.LoginScreen
import com.camila.pet_project.ui.petlistscreen.PetListScreen

enum class Routes(val route: String) {
    Login("login"),
    PetList("petList/{userId}") {
        fun createRoute(userId: Int) = "petList/$userId"
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Routes.PetList.route,
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            PetListScreen(userId = userId)
        }
    }
}