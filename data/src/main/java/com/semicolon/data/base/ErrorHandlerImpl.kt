package com.semicolon.data.base

import com.semicolon.domain.base.Error
import com.semicolon.domain.base.ErrorHandler
import retrofit2.HttpException

class ErrorHandlerImpl : ErrorHandler {

    override fun handle(throwable: Throwable): Error =
        if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> Error.BAD_REQUEST
                401 -> Error.UNAUTHORIZED
                403 -> Error.FORBIDDEN
                404 -> Error.NOT_FOUND
                409 -> Error.CONFLICT
                500, 503 -> Error.SERVER_ERROR
                else -> Error.NETWORK_ERROR
            }
        } else Error.NETWORK_ERROR
}