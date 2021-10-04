package com.semicolon.domain.usecase.account

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.param.ReportParam
import com.semicolon.domain.service.AccountService
import io.reactivex.Single

class ReportUserUseCase(
    private val accountService: AccountService
) : UseCase<ReportParam, Resource<Unit>>() {

    override fun interact(data: ReportParam?): Single<Resource<Unit>> =
        accountService.reportUser(data!!)
}