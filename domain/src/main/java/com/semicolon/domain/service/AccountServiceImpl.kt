package com.semicolon.domain.service

import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.toResource
import com.semicolon.domain.base.toSingleResource
import com.semicolon.domain.entity.InterestedEntity
import com.semicolon.domain.param.CoordinateParam
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.param.ReportParam
import com.semicolon.domain.repository.AccountRepository
import io.reactivex.Single

class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val errorHandler: ErrorHandler
) : AccountService {

    override fun registerAccount(registerAccountParam: RegisterAccountParam): Single<Resource<Unit>> =
        accountRepository.registerAccount(registerAccountParam).toSingleResource(errorHandler)

    override fun checkEmailDuplication(email: String): Single<Resource<Unit>> =
        accountRepository.checkEmailDuplication(email).toSingleResource(errorHandler)

    override fun checkNicknameDuplication(nickname: String): Single<Resource<Unit>> =
        accountRepository.checkNicknameDuplication(nickname).toSingleResource(errorHandler)

    override fun saveCoordinate(coordinateParam: CoordinateParam): Single<Resource<Unit>> =
        accountRepository.saveCoordinate(coordinateParam).toSingleResource(errorHandler)

    override fun reportUser(reportParam: ReportParam): Single<Resource<Unit>> =
        accountRepository.reportUser(reportParam).toSingleResource(errorHandler)

    override fun hasInterested(id: Int): Single<Resource<InterestedEntity>> =
        accountRepository.hasInterested(id).toResource(errorHandler)
}