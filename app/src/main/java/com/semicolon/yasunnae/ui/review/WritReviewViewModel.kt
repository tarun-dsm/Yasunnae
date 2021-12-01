package com.semicolon.yasunnae.ui.review

import androidx.lifecycle.MutableLiveData
import com.semicolon.domain.base.Error
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.param.ReviewParam
import com.semicolon.domain.usecase.auth.TokenRefreshUseCase
import com.semicolon.domain.usecase.review.DeleteReviewUseCase
import com.semicolon.domain.usecase.review.EditReviewUseCase
import com.semicolon.domain.usecase.review.WriteReviewUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class WritReviewViewModel @Inject constructor(
    private val writeReviewUseCase: WriteReviewUseCase,
    private val editReviewUseCase: EditReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
    private val tokenRefreshUseCase: TokenRefreshUseCase
) : BaseViewModel() {


    val writeReviewSuccessEvent = SingleLiveEvent<Unit>()
    val fixReviewSuccessEvent = SingleLiveEvent<Unit>()
    val badRequestEvent = SingleLiveEvent<Unit>()
    val retryEvent = SingleLiveEvent<Unit>()
    val needToLoginEvent = SingleLiveEvent<Unit>()
    val notFoundEvent = SingleLiveEvent<Unit>()
    val unknownEvent = SingleLiveEvent<Unit>()

    fun writeReview(reviewParam: ReviewParam) {
        val result = writeReviewUseCase.interact(reviewParam)
        val resultObserver = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> writeReviewSuccessEvent.call()
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
        observeSingle(result, resultObserver)
    }

    fun fixReview(editreviewParam: ReviewParam) {
        val result = editReviewUseCase.interact(editreviewParam)
        val resultObserver = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> fixReviewSuccessEvent.call()
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