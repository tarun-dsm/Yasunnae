package com.semicolon.domain.service

import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.toSingleResource
import com.semicolon.domain.repository.AuthRepository
import io.reactivex.Single

class AuthServiceImpl(
    private val authRepository: AuthRepository,
    private val errorHandler: ErrorHandler
) : AuthService {

    override fun login(email: String, password: String): Single<Resource<Unit>> =
        authRepository.login(email, password).toSingleResource(errorHandler)

    override fun tokenRefresh(): Single<Resource<Unit>> =
        authRepository.tokenRefresh().toSingleResource(errorHandler)
}