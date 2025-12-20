package com.camila.pet_project.ui.navigation

sealed class NavigationEvent {

    data class NavigateToPetList(val tutorId: Int) : NavigationEvent()
    object NavigateToRegister : NavigationEvent()
}