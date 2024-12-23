package com.camila.pet_project.ui.navigation

sealed class NavigationEvent {

    data object NavigateToPetList : NavigationEvent()
}