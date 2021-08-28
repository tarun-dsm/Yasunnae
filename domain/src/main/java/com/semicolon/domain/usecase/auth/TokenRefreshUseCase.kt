package com.semicolon.domain.usecase.auth

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.service.AuthService
import io.reactivex.Single

class TokenRefreshUseCase9(
    private val authService: AuthService
) : UseCase<Unit, Resource<Unit>>() {

    override fun interact(data: Unit): Single<Resource<Unit>> =
        authService.tokenRefresh()
}