package com.semicolon.domain.enum

enum class AnimalType {
    MAMMEL, BIRD, REPTILES, AMPHIBIANS, FISH, ARTHROPODS, WRONG_TYPE
}

fun String.toAnimalType() =
    when (this) {
        "MAMMEL" -> AnimalType.MAMMEL
        "BIRD" -> AnimalType.BIRD
        "REPTILES" -> AnimalType.REPTILES
        "AMPHIBIANS" -> AnimalType.AMPHIBIANS
        "FISH" -> AnimalType.FISH
        "ARTHROPODS" -> AnimalType.ARTHROPODS
        else -> AnimalType.WRONG_TYPE
    }