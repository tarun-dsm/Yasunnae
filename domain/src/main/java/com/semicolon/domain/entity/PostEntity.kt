package com.semicolon.domain.entity

import com.semicolon.domain.enum.AnimalType

data class PostEntity(

    val id: Int,

    val title: String,

    val firstImagePath: String,

    val administrationDivision: String,

    val protectionStartDate: String,

    val protectionEndDate: String,

    val animalType: AnimalType
)
