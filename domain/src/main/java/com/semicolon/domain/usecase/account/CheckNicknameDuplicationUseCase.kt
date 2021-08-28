package com.semicolon.domain.usecase.account

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.service.AccountService
import io.reactivex.Single

class CheckNicknameDuplicationUseCase(
    private val accountService: AccountService
) : UseCase<String, Resource<Unit>>() {

    override fun interact(data: String): Single<Resource<Unit>> =
        accountService.checkNicknameDuplication(data)
}