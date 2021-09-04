package com.semicolon.data.repository

import com.semicolon.data.datasource.AccountDataSource
import com.semicolon.domain.param.CoordinateParam
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.param.ReportParam
import com.semicolon.domain.repository.AccountRepository
import io.reactivex.Completable

class AccountRepositoryImpl(
    private val accountDataSource: AccountDataSource
) : AccountRepository {

    override fun registerAccount(registerAccountParam: RegisterAccountParam): Completable =
        accountDataSource.registerAccount(registerAccountParam)

    override fun checkEmailDuplication(email: String): Completable =
        accountDataSource.checkEmailDuplication(email)

    override fun checkNicknameDuplication(nickname: String): Completable =
        accountDataSource.checkNicknameDuplication(nickname)

    override fun saveCoordinate(coordinateParam: CoordinateParam): Completable =
        accountDataSource.saveCoordinate(coordinateParam)

    override fun reportUser(reportParam: ReportParam): Completable =
        accountDataSource.reportUser(reportParam)
}