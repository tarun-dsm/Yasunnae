package com.semicolon.data.service

import com.semicolon.domain.base.Error
import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.repository.ApplicationRepository
import com.semicolon.domain.service.ApplicationService
import com.semicolon.domain.service.ApplicationServiceImpl
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class ApplicationServiceUnitTest {

    @Mock
    private lateinit var applicationRepository: ApplicationRepository

    @Mock
    private lateinit var errorHandler: ErrorHandler

    private lateinit var applicationService: ApplicationService

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        applicationService = ApplicationServiceImpl(applicationRepository, errorHandler)
    }

    @Test
    fun sendApplicationSuccessTest() {
        val id = 1234

        `when`(applicationRepository.sendApplication(id))
            .thenReturn(Completable.complete())

        applicationService.sendApplication(id).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun sendApplicationErrorTest() {
        val exception = Exception()
        val errorMessage = Error.NETWORK_ERROR
        val id = 1234

        `when`(applicationRepository.sendApplication(id))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        applicationService.sendApplication(id).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun cancelApplicationSuccessTest() {
        val id = 1234

        `when`(applicationRepository.cancelApplication(id))
            .thenReturn(Completable.complete())

        applicationService.cancelApplication(id).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun cancelApplicationErrorTest() {
        val exception = Exception()
        val errorMessage = Error.NETWORK_ERROR
        val id = 1234

        `when`(applicationRepository.cancelApplication(id))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        applicationService.cancelApplication(id).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun acceptApplicationSuccessTest() {
        val id = 1234

        `when`(applicationRepository.acceptApplication(id))
            .thenReturn(Completable.complete())

        applicationService.acceptApplication(id).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun acceptApplicationErrorTest() {
        val exception = Exception()
        val errorMessage = Error.NETWORK_ERROR
        val id = 1234

        `when`(applicationRepository.acceptApplication(id))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        applicationService.acceptApplication(id).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun getMyApplicationListSuccessTest() {
        `when`(applicationRepository.getMyApplicationList())
            .thenReturn(Single.just(ArrayList()))

        applicationService.getMyApplicationList().test()
            .assertValue(Resource.success(ArrayList()))
    }

    @Test
    fun getMyApplicationListErrorTest() {
        val exception = Exception()
        val errorMessage = Error.NETWORK_ERROR

        `when`(applicationRepository.getMyApplicationList())
            .thenReturn(Single.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        applicationService.getMyApplicationList().test()
            .assertValue(Resource.error(errorMessage))
    }
}