package com.camila.pet_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.camila.pet_project.ui.addpetscreen.AddPetScreen
import com.camila.pet_project.ui.login.LoginScreen
import com.camila.pet_project.presentation.tutor_register.TutorRegisterScreen
import com.camila.pet_project.ui.petlistscreen.PetListScreen

enum class Routes(val route: String) {
    Login("login"),
    Register("register"),
    PetList("petList/{tutorId}"),
    AddPet("addPet/{tutorId}");

    companion object {
        fun petListRoute(tutorId: Int) = "petList/$tutorId"
        fun addPetRoute(tutorId: Int) = "addPet/$tutorId"
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
        composable(Routes.Register.route) {
            TutorRegisterScreen(
                onNavigateToPetList = { tutorId ->
                    navController.navigate(Routes.petListRoute(tutorId)) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Routes.PetList.route,
            arguments = listOf(
                navArgument("tutorId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val tutorId = backStackEntry.arguments?.getInt("tutorId") ?: 0
            PetListScreen(
                tutorId = tutorId,
                onAddPetClick = {
                    navController.navigate(Routes.addPetRoute(tutorId))
                }
            )
        }
        composable(
            route = Routes.AddPet.route,
            arguments = listOf(
                navArgument("tutorId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val tutorId = backStackEntry.arguments?.getInt("tutorId") ?: 0
            AddPetScreen(
                tutorId = tutorId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
