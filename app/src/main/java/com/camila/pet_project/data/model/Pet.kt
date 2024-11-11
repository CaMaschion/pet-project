package com.camila.pet_project.data.model

data class Pet (
    var id: Int = 0,
    var name: String = "",
    var type: String = "",
    var breed: String = "",
    var age: Int = 0,
    var description: String = "",
    var userId: Int = 0,
    val vaccines: MutableList<Vaccine> = mutableListOf()
)

