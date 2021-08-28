package com.semicolon.domain.base

data class Resource<out T>(
    val status: ResourceStatus,
    val data: T?,
    val message: Error?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ResourceStatus.SUCCESS, data, null)
        }

        fun <T> error(message: Error, data: T?): Resource<T> {
            return Resource(ResourceStatus.ERROR, data, message)
        }
    }
}
