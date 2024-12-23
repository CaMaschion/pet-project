package com.camila.pet_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.camila.pet_project.ui.login.LoginScreen
import com.camila.pet_project.ui.petlistscreen.PetListScreen

enum class Routes(val route: String) {
    Login("login"),
    PetList("petList")
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
            LoginScreen(navController = navController) {
                navController.navigate(Routes.PetList.route)
            }
        }
        composable(Routes.PetList.route) {
            PetListScreen()
        }
    }
}