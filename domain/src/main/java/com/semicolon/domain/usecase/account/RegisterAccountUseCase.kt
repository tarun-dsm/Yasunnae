package com.semicolon.domain.usecase.account

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.service.AccountService
import io.reactivex.Single

class RegisterAccountUseCase(
    private val accountService: AccountService
): UseCase<RegisterAccountParam, Resource<Unit>>() {

    override fun interact(data: RegisterAccountParam): Single<Resource<Unit>> =
        accountService.registerAccount(data)
}