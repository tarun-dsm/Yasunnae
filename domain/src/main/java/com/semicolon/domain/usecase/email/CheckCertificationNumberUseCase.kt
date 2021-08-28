package com.semicolon.domain.usecase.email

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.param.EmailCertificationParam
import com.semicolon.domain.service.EmailService
import io.reactivex.Single

class CheckCertificationNumberUseCase(
    private val emailService: EmailService
) : UseCase<EmailCertificationParam, Resource<Unit>>() {

    override fun interact(data: EmailCertificationParam): Single<Resource<Unit>> =
        emailService.checkCertificationNumber(data)
}