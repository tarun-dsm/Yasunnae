package com.semicolon.domain.param

import com.semicolon.domain.enum.AnimalType
import com.semicolon.domain.enum.Sex

data class PostParam(

    val title: String,

    val protectionStartDate: String,

    val protectionEndDate: String,

    val applicationEndDate: String,

    val description: String,

    val contactInfo: String,

    val petName: String,

    val petSpecies: String,

    val petSex: Sex,

    val animalType: AnimalType
)