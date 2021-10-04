package com.semicolon.data.usecase

import com.semicolon.domain.base.Resource
import com.semicolon.domain.entity.ApplicationEntity
import com.semicolon.domain.service.ApplicationService
import com.semicolon.domain.usecase.application.AcceptApplicationUseCase
import com.semicolon.domain.usecase.application.CancelApplicationUseCase
import com.semicolon.domain.usecase.application.GetMyApplicationListUseCase
import com.semicolon.domain.usecase.application.SendApplicationUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ApplicationUseCasesUnitTest {

    @Mock
    private lateinit var applicationService: ApplicationService

    private lateinit var acceptApplicationUseCase: AcceptApplicationUseCase
    private lateinit var cancelApplicationUseCase: CancelApplicationUseCase
    private lateinit var getMyApplicationListUseCase: GetMyApplicationListUseCase
    private lateinit var sendApplicationUseCase: SendApplicationUseCase

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        acceptApplicationUseCase = AcceptApplicationUseCase(applicationService)
        cancelApplicationUseCase = CancelApplicationUseCase(applicationService)
        getMyApplicationListUseCase = GetMyApplicationListUseCase(applicationService)
        sendApplicationUseCase = SendApplicationUseCase(applicationService)
    }

    @Test
    fun acceptApplicationTest() {
        val id = 925

        `when`(applicationService.acceptApplication(id))
            .thenReturn(Single.just(Resource.success(Unit)))

        acceptApplicationUseCase.interact(id).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun cancelApplicationTest() {
        val id = 925

        `when`(applicationService.cancelApplication(id))
            .thenReturn(Single.just(Resource.success(Unit)))

        cancelApplicationUseCase.interact(id).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun getMyApplicationListTest() {
        `when`(applicationService.getMyApplicationList())
            .thenReturn(Single.just(Resource.success(ArrayList())))

        getMyApplicationListUseCase.interact(Unit).test()
            .assertValue(Resource.success(ArrayList()))
    }

    @Test
    fun sendApplicationTest() {
        val id = 925

        `when`(applicationService.sendApplication(id))
            .thenReturn(Single.just(Resource.success(Unit)))

        sendApplicationUseCase.interact(id).test()
            .assertValue(Resource.success(Unit))
    }
}