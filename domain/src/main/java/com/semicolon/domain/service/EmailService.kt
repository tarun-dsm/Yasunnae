package com.semicolon.domain.service

import com.semicolon.domain.base.Resource
import io.reactivex.Single

interface EmailService {

    fun checkCertificationNumber(email: String, number: String): Single<Resource<Unit>>

    fun sendCertificationMail(email: String): Single<Resource<Unit>>
}