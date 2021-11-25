package com.semicolon.data.datasource

import com.semicolon.data.remote.api.EmailApi
import com.semicolon.data.remote.request.EmailRequest
import com.semicolon.data.remote.request.SendCertificationEmailRequest
import com.semicolon.domain.param.EmailCertificationParam
import io.reactivex.Completable

class EmailDataSourceImpl(
    private val emailApi: EmailApi
) : EmailDataSource {

    override fun checkCertificationNumber(emailCertificationParam: EmailCertificationParam): Completable =
        emailApi.checkCertificationNumber(EmailRequest(emailCertificationParam.email, emailCertificationParam.number))

    override fun sendCertificationEmail(email: String): Completable =
        emailApi.sendCertificationEmail(SendCertificationEmailRequest(email))
}