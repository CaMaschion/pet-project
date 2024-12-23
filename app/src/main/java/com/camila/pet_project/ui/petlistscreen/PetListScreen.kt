package com.camila.pet_project.ui.petlistscreen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PetListScreen() {

    Scaffold {
        Text(
            text = "Pet List Screen",
        )
    }
}

@Preview
@Composable
fun PetListScreenPreview() {
    PetListScreen()
}