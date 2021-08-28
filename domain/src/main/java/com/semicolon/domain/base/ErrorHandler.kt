package com.semicolon.domain.base

interface ErrorHandler {
    fun handle(throwable: Throwable): Error
}