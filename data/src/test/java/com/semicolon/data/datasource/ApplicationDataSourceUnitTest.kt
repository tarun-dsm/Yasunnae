package com.semicolon.data.datasource

import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.ApplicationApi
import com.semicolon.data.remote.response.MyApplicationResponse
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class ApplicationDataSourceUnitTest {

    @Mock
    private lateinit var applicationApi: ApplicationApi

    @Mock
    private lateinit var tokenStorage: TokenStorage

    private lateinit var applicationDataSource: ApplicationDataSource

    private val token = "abc.def.ghi"

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        applicationDataSource = ApplicationDataSourceImpl(tokenStorage, applicationApi)

        `when`(tokenStorage.getAccessToken())
            .thenReturn(token)
    }

    @Test
    fun sendApplicationSuccessTest() {
        val id = 1234

        `when`(applicationApi.sendApplication(token, id))
            .thenReturn(Completable.complete())

        applicationDataSource.sendApplication(id).test()
            .assertComplete()
    }

    @Test
    fun sendApplicationErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(applicationApi.sendApplication(token, id))
            .thenReturn(Completable.error(exception))

        applicationDataSource.sendApplication(id).test()
            .assertError(exception)
    }

    @Test
    fun cancelApplicationSuccessTest() {
        val id = 1234

        `when`(applicationApi.cancelApplication(token, id))
            .thenReturn(Completable.complete())

        applicationDataSource.cancelApplication(id).test()
            .assertComplete()
    }

    @Test
    fun cancelApplicationErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(applicationApi.cancelApplication(token, id))
            .thenReturn(Completable.error(exception))

        applicationDataSource.cancelApplication(id).test()
            .assertError(exception)
    }

    @Test
    fun acceptApplicationSuccessTest() {
        val id = 1234

        `when`(applicationApi.acceptApplication(token, id))
            .thenReturn(Completable.complete())

        applicationDataSource.acceptApplication(id).test()
            .assertComplete()
    }

    @Test
    fun acceptApplicationErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(applicationApi.acceptApplication(token, id))
            .thenReturn(Completable.error(exception))

        applicationDataSource.acceptApplication(id).test()
            .assertError(exception)
    }

    @Test
    fun getMyApplicationSuccessTest() {
        val myApplicationResponse = MyApplicationResponse(
            ArrayList()
        )

        `when`(applicationApi.getMyApplication(token))
            .thenReturn(Single.just(myApplicationResponse))

        applicationDataSource.getMyApplication().test()
            .assertValue(myApplicationResponse)
    }

    @Test
    fun getMyApplicationErrorTest() {
        val exception = Exception()

        `when`(applicationApi.getMyApplication(token))
            .thenReturn(Single.error(exception))

        applicationDataSource.getMyApplication().test()
            .assertError(exception)
    }
}