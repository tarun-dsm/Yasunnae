package com.semicolon.domain.service

import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.toSingleResource
import com.semicolon.domain.param.LoginParam
import com.semicolon.domain.repository.AuthRepository
import io.reactivex.Single
import kotlin.math.log

class AuthServiceImpl(
    private val authRepository: AuthRepository,
    private val errorHandler: ErrorHandler
) : AuthService {

    override fun login(loginParam: LoginParam): Single<Resource<Unit>> =
        authRepository.login(loginParam).toSingleResource(errorHandler)

    override fun tokenRefresh(): Single<Resource<Unit>> =
        authRepository.tokenRefresh().toSingleResource(errorHandler)

    override fun logout(): Single<Resource<Unit>> =
        authRepository.tokenRefresh().toSingleResource(errorHandler)
}