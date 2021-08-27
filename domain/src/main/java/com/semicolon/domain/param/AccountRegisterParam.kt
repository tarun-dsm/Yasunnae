package com.semicolon.domain.param

data class AccountRegisterParam(

    val email: String,

    val password: String,

    val nickname: String,

    val age: Int,

    val sex: Sex,

    val isExperienceRaisingPet: Boolean,

    val experience: String
)

sealed class Sex(val type: String) {
    object MALE : Sex("MALE")
    object FEMALE : Sex("FEMALE")
}
