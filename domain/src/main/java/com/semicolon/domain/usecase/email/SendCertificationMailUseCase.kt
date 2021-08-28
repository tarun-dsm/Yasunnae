package com.semicolon.domain.usecase.email

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.service.EmailService
import io.reactivex.Single

class SendCertificationMailUseCase(
    private val emailService: EmailService
) : UseCase<String, Resource<Unit>>() {

    override fun interact(data: String?): Single<Resource<Unit>> =
        emailService.sendCertificationMail(data!!)
}