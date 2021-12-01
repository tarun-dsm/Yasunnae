package com.semicolon.domain.param

import com.semicolon.domain.enum.Sex

data class RegisterAccountParam(

    val email: String,

    val password: String,

    val nickname: String,

    val age: Int,

    val sex: Sex,

    val isExperienceRaisingPet: Boolean,

    val experience: String?
)
