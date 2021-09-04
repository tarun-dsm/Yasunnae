package com.semicolon.data.datasource

import com.semicolon.data.remote.request.SendCertificationEmailRequest
import com.semicolon.domain.param.EmailCertificationParam
import io.reactivex.Completable

interface EmailDataSource {

    fun checkCertificationNumber(emailCertificationParam: EmailCertificationParam): Completable

    fun sendCertificationEmail(email: String): Completable
}