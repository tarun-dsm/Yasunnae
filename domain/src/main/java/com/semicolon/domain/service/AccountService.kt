package com.semicolon.domain.service

import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.CoordinateParam
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.param.ReportParam
import io.reactivex.Single

interface AccountService {

    fun registerAccount(registerAccountParam: RegisterAccountParam): Single<Resource<Unit>>

    fun checkEmailDuplication(email: String): Single<Resource<Unit>>

    fun checkNicknameDuplication(nickname: String): Single<Resource<Unit>>

    fun saveCoordinate(coordinateParam: CoordinateParam): Single<Resource<Unit>>

    fun reportUser(reportParam: ReportParam): Single<Resource<Unit>>
}