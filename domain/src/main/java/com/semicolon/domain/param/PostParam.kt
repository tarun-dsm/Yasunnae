package com.semicolon.domain.param

data class PostParam (

    val title: String,

    val protectionStartDate: String,

    val protectionEndDate: String,

    val applicationEndDate: String,

    val description: String,

    val contactInfo: String,

    val petName: String,

    val petSpecies: String,

    val petSex: String
)