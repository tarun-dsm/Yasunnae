package com.semicolon.domain.base

import io.reactivex.Single

abstract class UseCase<in T, E> {
    abstract fun interact(data: T): Single<E>
}