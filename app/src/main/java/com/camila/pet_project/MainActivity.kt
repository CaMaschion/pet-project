package com.camila.pet_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.camila.pet_project.ui.navigation.AppNavHost
import com.camila.pet_project.ui.theme.PetprojectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    PetprojectTheme {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyApp() {
    val navController = rememberNavController()
    MyApp {
        AppNavHost(navController = navController)
    }
}