package com.semicolon.data.base

import com.semicolon.domain.base.Error
import com.semicolon.domain.base.ErrorHandler
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import java.net.ConnectException

class ErrorHandlerUnitTest {

    @Mock
    private lateinit var httpException: HttpException

    @Mock
    private lateinit var connectException: ConnectException

    private lateinit var errorHandler: ErrorHandler

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        errorHandler = ErrorHandlerImpl()
    }

    @Test
    fun badRequestTest() {
        `when`(httpException.code())
            .thenReturn(400)

        Assert.assertEquals(
            errorHandler.handle(httpException),
            Error.BAD_REQUEST
        )
    }

    @Test
    fun unauthorizedTest() {
        `when`(httpException.code())
            .thenReturn(401)

        Assert.assertEquals(
            errorHandler.handle(httpException),
            Error.UNAUTHORIZED
        )
    }

    @Test
    fun forbiddenTest() {
        `when`(httpException.code())
            .thenReturn(403)

        Assert.assertEquals(
            errorHandler.handle(httpException),
            Error.FORBIDDEN
        )
    }

    @Test
    fun notFoundTest() {
        `when`(httpException.code())
            .thenReturn(404)

        Assert.assertEquals(
            errorHandler.handle(httpException),
            Error.NOT_FOUND
        )
    }

    @Test
    fun conflictTest() {
        `when`(httpException.code())
            .thenReturn(409)

        Assert.assertEquals(
            errorHandler.handle(httpException),
            Error.CONFLICT
        )
    }

    @Test
    fun noExperienceTest() {
        `when`(httpException.code())
            .thenReturn(418)

        Assert.assertEquals(
            errorHandler.handle(httpException),
            Error.NO_EXPERIENCE
        )
    }

    @Test
    fun serverErrorTest() {
        `when`(httpException.code())
            .thenReturn(500)

        Assert.assertEquals(
            errorHandler.handle(httpException),
            Error.SERVER_ERROR
        )
    }

    @Test
    fun networkErrorTest() {
        Assert.assertEquals(
            errorHandler.handle(connectException),
            Error.NETWORK_ERROR
        )
    }
}