package com.semicolon.data.datasource

import com.semicolon.data.remote.api.EmailApi
import com.semicolon.data.remote.request.EmailRequest
import com.semicolon.data.remote.request.SendCertificationEmailRequest
import com.semicolon.domain.param.EmailCertificationParam
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class EmailDataSourceUnitTest {

    @Mock
    private lateinit var emailApi: EmailApi

    private lateinit var emailDataSource: EmailDataSource

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        emailDataSource = EmailDataSourceImpl(emailApi)
    }

    @Test
    fun checkCertificationNumberSuccessTest() {
        val emailCertificationParam = EmailCertificationParam(
            "shw0471@naver.com",
            "1234"
        )

        `when`(
            emailApi.checkCertificationNumber(
                EmailRequest( emailCertificationParam.email, emailCertificationParam.number)
            )
        ).thenReturn(Completable.complete())

        emailDataSource.checkCertificationNumber(emailCertificationParam).test()
            .assertComplete()
    }

    @Test
    fun checkCertificationNumberErrorTest() {
        val emailCertificationParam = EmailCertificationParam(
            "shw0471@naver.com",
            "1234"
        )
        val exception = Exception()

        `when`(
            emailApi.checkCertificationNumber(
                EmailRequest( emailCertificationParam.email, emailCertificationParam.number)
            )
        ).thenReturn(Completable.error(exception))

        emailDataSource.checkCertificationNumber(emailCertificationParam).test()
            .assertError(exception)
    }

    @Test
    fun sendCertificationEmailSuccessTest() {
        val email = "shw0471"

        `when`(emailApi.sendCertificationEmail(SendCertificationEmailRequest(email)))
            .thenReturn(Completable.complete())

        emailDataSource.sendCertificationEmail(email).test()
            .assertComplete()
    }

    @Test
    fun sendCertificationEmailErrorTest() {
        val email = "shw0471"
        val exception = Exception()

        `when`(emailApi.sendCertificationEmail(SendCertificationEmailRequest(email)))
            .thenReturn(Completable.error(exception))

        emailDataSource.sendCertificationEmail(email).test()
            .assertError(exception)
    }
}