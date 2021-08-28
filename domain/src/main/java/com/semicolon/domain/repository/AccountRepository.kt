package com.semicolon.domain.repository

import com.semicolon.domain.param.RegisterAccountParam
import io.reactivex.Completable

interface AccountRepository {

    fun registerAccount(registerAccountParam: RegisterAccountParam): Completable

    fun checkEmailDuplication(email: String): Completable

    fun checkNicknameDuplication(nickname: String): Completable

    fun saveCoordinate(longitude: Double, latitude: Double): Completable
}