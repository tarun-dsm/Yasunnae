package com.semicolon.domain.repository

import com.semicolon.domain.param.EmailCertificationParam
import io.reactivex.Completable

interface EmailRepository {

    fun checkCertificationNumber(emailCertificationParam: EmailCertificationParam): Completable

    fun sendCertificationMail(email: String): Completable
}