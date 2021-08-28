package com.semicolon.domain.usecase.account

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.param.CoordinateParam
import com.semicolon.domain.service.AccountService
import io.reactivex.Single

class SaveCoordinateUseCase(
    private val accountService: AccountService
) : UseCase<CoordinateParam, Resource<Unit>>() {

    override fun interact(data: CoordinateParam): Single<Resource<Unit>> =
        accountService.saveCoordinate(data)
}