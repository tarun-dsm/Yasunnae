package com.semicolon.domain.usecase.auth

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.param.LoginParam
import com.semicolon.domain.service.AuthService
import io.reactivex.Single

class LoginUseCase(
    private val authService: AuthService
) : UseCase<LoginParam, Resource<Unit>>() {

    override fun interact(data: LoginParam?): Single<Resource<Unit>> =
        authService.login(data!!)
}