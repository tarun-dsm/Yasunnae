package com.semicolon.domain.service

import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.toSingleResource
import com.semicolon.domain.repository.EmailRepository
import io.reactivex.Single

class EmailServiceImpl(
    private val emailRepository: EmailRepository,
    private val errorHandler: ErrorHandler
): EmailService {
    override fun checkCertificationNumber(email: String, number: String): Single<Resource<Unit>> =
        emailRepository.checkCertificationNumber(email, number).toSingleResource(errorHandler)

    override fun sendCertificationMail(email: String): Single<Resource<Unit>> =
        emailRepository.sendCertificationMail(email).toSingleResource(errorHandler)
}