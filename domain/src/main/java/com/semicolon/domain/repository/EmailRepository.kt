package com.semicolon.domain.repository

import io.reactivex.Completable

interface EmailRepository {

    fun checkCertificationNumber(email: String, number: String): Completable

    fun sendCertificationMail(email: String): Completable
}