package com.semicolon.yasunnae.ui.review

import androidx.lifecycle.MutableLiveData
import com.semicolon.domain.base.Error
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.param.ReviewParam
import com.semicolon.domain.usecase.auth.TokenRefreshUseCase
import com.semicolon.domain.usecase.review.EditReviewUseCase
import com.semicolon.domain.usecase.review.WriteReviewUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class WritReviewViewModel @Inject constructor(
    private val writeReviewUseCase: WriteReviewUseCase,
    private val editReviewUseCase: EditReviewUseCase,
    private val tokenRefreshUseCase: TokenRefreshUseCase

) : BaseViewModel() {
    val postIdLiveData = MutableLiveData<Int>()
    val badRequestEvent = SingleLiveEvent<Unit>()
    val retryEvent = SingleLiveEvent<Unit>()
    val needToLoginEvent = SingleLiveEvent<Unit>()
    val notFoundEvent = SingleLiveEvent<Unit>()
    val unknownEvent = SingleLiveEvent<Unit>()

    private val resultObserver = object : DisposableSingleObserver<Resource<Int>>() {
        override fun onSuccess(t: Resource<Int>) {
            when (t.status) {
                ResourceStatus.SUCCESS -> postIdLiveData.value = t.data!!
                ResourceStatus.ERROR -> when (t.message) {
                    Error.BAD_REQUEST -> badRequestEvent.call()
                    Error.UNAUTHORIZED -> {
                        tokenRefresh()
                        retryEvent.call()
                    }
                    Error.NOT_FOUND -> notFoundEvent.call()
                    else -> unknownEvent.call()
                }
            }
        }

        override fun onError(e: Throwable) {
            unknownEvent.call()
        }
    }

    fun writeReview(reviewParam: ReviewParam) {
        val result = writeReviewUseCase.interact(reviewParam)
        observeSingle(result, resultObserver)
    }

    fun fixReview(editreviewParam: ReviewParam) {
        val result = editReviewUseCase.interact(editreviewParam)
        observeSingle(result, resultObserver)
    }

    private fun tokenRefresh() {
        val result = tokenRefreshUseCase.interact(Unit)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                if (t.status != ResourceStatus.SUCCESS)
                    needToLoginEvent.call()
            }

            override fun onError(e: Throwable) {
                needToLoginEvent.call()
            }
        }
        observeSingle(result, observer)
    }
}