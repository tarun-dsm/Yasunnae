package com.semicolon.data.datasource

import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.AccountApi
import com.semicolon.data.remote.request.ReportRequest
import com.semicolon.data.remote.request.toRequestParam
import com.semicolon.domain.param.CoordinateParam
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.param.ReportParam
import io.reactivex.Completable

class AccountDataSourceImpl(
    private val tokenStorage: TokenStorage,
    private val accountApi: AccountApi
) : AccountDataSource {

    override fun registerAccount(registerAccountParam: RegisterAccountParam): Completable {
        return accountApi.registerAccount(registerAccountParam.toRequestParam())
            .map { tokenStorage.saveToken(it.accessToken, it.refreshToken) }.ignoreElement()
    }

    override fun checkEmailDuplication(email: String): Completable =
        accountApi.checkEmailDuplication(email)

    override fun checkNicknameDuplication(nickname: String): Completable =
        accountApi.checkNicknameDuplication(nickname)

    override fun saveCoordinate(coordinateParam: CoordinateParam): Completable =
        accountApi.saveCoordinate(
            tokenStorage.getAccessToken(),
            coordinateParam.toRequestParam()
        )

    override fun reportUser(reportParam: ReportParam): Completable =
        accountApi.reportUser(
            tokenStorage.getAccessToken(),
            reportParam.id,
            ReportRequest(reportParam.comment)
        )
}