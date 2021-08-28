package com.semicolon.domain.service

import com.semicolon.domain.base.Resource
import io.reactivex.Single

interface AuthService {

    fun login(email: String, password: String): Single<Resource<Unit>>

    fun tokenRefresh(): Single<Resource<Unit>>
}