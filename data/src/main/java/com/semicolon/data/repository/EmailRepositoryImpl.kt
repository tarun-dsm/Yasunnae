package com.semicolon.data.repository

import com.semicolon.data.datasource.EmailDataSource
import com.semicolon.domain.param.EmailCertificationParam
import com.semicolon.domain.repository.EmailRepository
import io.reactivex.Completable

class EmailRepositoryImpl(
    private val emailDataSource: EmailDataSource
) : EmailRepository {

    override fun checkCertificationNumber(emailCertificationParam: EmailCertificationParam): Completable =
        emailDataSource.checkCertificationNumber(emailCertificationParam)

    override fun sendCertificationMail(email: String): Completable =
        emailDataSource.sendCertificationEmail(email)
}