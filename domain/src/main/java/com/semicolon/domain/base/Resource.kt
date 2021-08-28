package com.semicolon.domain.base

import io.reactivex.Completable
import io.reactivex.Single
import org.jetbrains.annotations.NotNull

data class Resource<out T>(
    val status: ResourceStatus,
    val data: T?,
    val message: Error?
) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(ResourceStatus.SUCCESS, data, null)
        }

        fun <T> error(message: Error, data: T? = null): Resource<T> {
            return Resource(ResourceStatus.ERROR, data, message)
        }
    }
}

fun <T : Any> Single<T>.toResource(
    handler: ErrorHandler
): Single<Resource<T>> =
    this.map {
        Resource.success(it)
    }.onErrorReturn {
        Resource.error(handler.handle(it))
    }

fun Completable.toSingleResource(
    handler: ErrorHandler
): Single<Resource<Unit>> =
    this.toSingle { }.toResource(handler)