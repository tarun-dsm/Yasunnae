package com.semicolon.domain.repository

import io.reactivex.Completable

interface AuthRepository {

    fun login(email: String, password: String): Completable

    fun tokenRefresh(): Completable
}