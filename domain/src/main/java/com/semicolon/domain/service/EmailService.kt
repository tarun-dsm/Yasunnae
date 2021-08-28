package com.semicolon.domain.service

import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.EmailCertificationParam
import io.reactivex.Single

interface EmailService {

    fun checkCertificationNumber(emailCertificationParam: EmailCertificationParam): Single<Resource<Unit>>

    fun sendCertificationMail(email: String): Single<Resource<Unit>>
}