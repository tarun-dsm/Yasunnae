package com.semicolon.domain.service

import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.LoginParam
import io.reactivex.Single

interface AuthService {

    fun login(loginParam: LoginParam): Single<Resource<Unit>>

    fun tokenRefresh(): Single<Resource<Unit>>

    fun logout(): Single<Resource<Unit>>
}