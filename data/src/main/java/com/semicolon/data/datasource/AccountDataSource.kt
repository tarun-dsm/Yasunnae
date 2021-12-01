package com.semicolon.data.datasource

import com.semicolon.data.remote.request.CoordinateRequest
import com.semicolon.data.remote.response.HasInterestedResponse
import com.semicolon.domain.param.CoordinateParam
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.param.ReportParam
import io.reactivex.Completable
import io.reactivex.Single

interface AccountDataSource {

    fun registerAccount(registerAccountParam: RegisterAccountParam): Completable

    fun checkEmailDuplication(email: String): Completable

    fun checkNicknameDuplication(nickname: String): Completable

    fun saveCoordinate(coordinateParam: CoordinateParam): Completable

    fun reportUser(reportParam: ReportParam): Completable

    fun hasInterested(id: Int): Single<HasInterestedResponse>
}