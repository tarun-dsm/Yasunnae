package com.semicolon.data.repository

import com.semicolon.data.datasource.ApplicationDataSource
import com.semicolon.data.remote.response.MyApplicationResponse
import com.semicolon.domain.repository.ApplicationRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class ApplicationRepositoryUnitTest {

    @Mock
    private lateinit var applicationDataSource: ApplicationDataSource

    private lateinit var applicationRepository: ApplicationRepository

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        applicationRepository = ApplicationRepositoryImpl(applicationDataSource)
    }

    @Test
    fun sendApplicationSuccessTest() {
        val id = 1234

        `when`(applicationDataSource.sendApplication(id))
            .thenReturn(Completable.complete())

        applicationRepository.sendApplication(id).test()
            .assertComplete()
    }

    @Test
    fun sendApplicationErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(applicationDataSource.sendApplication(id))
            .thenReturn(Completable.error(exception))

        applicationRepository.sendApplication(id).test()
            .assertError(exception)
    }

    @Test
    fun cancelApplicationSuccessTest() {
        val id = 1234

        `when`(applicationDataSource.cancelApplication(id))
            .thenReturn(Completable.complete())

        applicationRepository.cancelApplication(id).test()
            .assertComplete()
    }

    @Test
    fun cancelApplicationErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(applicationDataSource.cancelApplication(id))
            .thenReturn(Completable.error(exception))

        applicationRepository.cancelApplication(id).test()
            .assertError(exception)
    }

    @Test
    fun acceptApplicationSuccessTest() {
        val id = 1234

        `when`(applicationDataSource.acceptApplication(id))
            .thenReturn(Completable.complete())

        applicationRepository.acceptApplication(id).test()
            .assertComplete()
    }

    @Test
    fun acceptApplicationErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(applicationDataSource.acceptApplication(id))
            .thenReturn(Completable.error(exception))

        applicationRepository.acceptApplication(id).test()
            .assertError(exception)
    }

    @Test
    fun getMyApplicationSuccessTest() {
        val myApplicationResponse = MyApplicationResponse(
            ArrayList()
        )

        `when`(applicationDataSource.getMyApplication())
            .thenReturn(Single.just(myApplicationResponse))

        applicationRepository.getMyApplicationList().test()
            .assertValue(ArrayList())
    }

    @Test
    fun getMyApplicationErrorTest() {
        val exception = Exception()

        `when`(applicationDataSource.getMyApplication())
            .thenReturn(Single.error(exception))

        applicationRepository.getMyApplicationList().test()
            .assertError(exception)
    }
}