package com.semicolon.domain.repository

import com.semicolon.domain.param.AccountRegisterParam
import io.reactivex.Completable

interface AccountRepository {

    fun accountRegister(accountRegisterParam: AccountRegisterParam): Completable

    fun checkEmailDuplication(email: String): Completable

    fun checkNicknameDuplication(nickname: String): Completable

    fun saveCoordinate(longitude: Double, latitude: Double): Completable
}