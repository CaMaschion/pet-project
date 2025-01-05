package com.camila.pet_project.ui.petlistscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.camila.pet_project.R
import com.camila.pet_project.components.PetCardComponent
import com.camila.pet_project.data.model.Pet

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PetListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meus Pets") }
            )
        }
    ) { paddingValues ->

        val petsMock = listOf(
            Pet(
                id = 0,
                name = "Bolinha",
                breed = "Vira-lata",
                age = 2,
            ),
            Pet(
                id = 0,
                name = "Bagheera",
                breed = "Vira-lata",
                age = 2,
            )
        )

        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            items(petsMock) { pet ->
                PetCardComponent(
                    petPhoto = R.drawable.pawprint,
                    petName = "Bolinha",
                    breed = "Vira-lata",
                    age = "${pet.age} anos",
                )
            }
        }
    }
}

@Preview
@Composable
fun PetListScreenPreview() {
    PetListScreen()
}