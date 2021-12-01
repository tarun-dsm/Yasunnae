package com.semicolon.domain.usecase.account

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.entity.InterestedEntity
import com.semicolon.domain.service.AccountService
import io.reactivex.Single

class HasInterestedUseCase(
    private val accountService: AccountService
) : UseCase<Int, Resource<InterestedEntity>>() {

    override fun interact(data: Int?): Single<Resource<InterestedEntity>> =
        accountService.hasInterested(data!!)
}