package com.semicolon.domain.repository

import com.semicolon.domain.param.LoginParam
import io.reactivex.Completable

interface AuthRepository {

    fun login(loginParam: LoginParam): Completable

    fun tokenRefresh(): Completable

    fun logout(): Completable
}