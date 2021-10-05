package com.semicolon.yasunnae.ui.postapplications

import androidx.lifecycle.MutableLiveData
import com.semicolon.domain.base.Error
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.entity.PostApplicationEntity
import com.semicolon.domain.usecase.application.AcceptApplicationUseCase
import com.semicolon.domain.usecase.auth.TokenRefreshUseCase
import com.semicolon.domain.usecase.post.GetPostApplicationUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class PostApplicationsViewModel @Inject constructor(
    private val getPostApplicationUseCase: GetPostApplicationUseCase,
    private val acceptApplicationUseCase: AcceptApplicationUseCase,
    private val tokenRefreshUseCase: TokenRefreshUseCase
) : BaseViewModel() {

    val postApplicationsLiveData = MutableLiveData<List<PostApplicationEntity>>()
    val retryGetPostApplicationEvent = SingleLiveEvent<Unit>()
    val needToLoginEvent = SingleLiveEvent<Unit>()
    val postNotFoundEvent = SingleLiveEvent<Unit>()
    val unknownErrorEvent = SingleLiveEvent<Unit>()
    val acceptApplicationSuccessEvent = SingleLiveEvent<Int>()
    val retryAcceptApplicationEvent = SingleLiveEvent<Unit>()
    val applicationNotFoundEvent = SingleLiveEvent<Unit>()

    fun getPostApplication(id: Int) {
        val result = getPostApplicationUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<List<PostApplicationEntity>>>() {
            override fun onSuccess(t: Resource<List<PostApplicationEntity>>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> postApplicationsLiveData.value = t.data!!
                    ResourceStatus.ERROR -> when (t.message) {
                        Error.UNAUTHORIZED -> {
                            tokenRefresh()
                            retryGetPostApplicationEvent.call()
                        }
                        Error.NOT_FOUND -> postNotFoundEvent.call()
                        else -> unknownErrorEvent.call()
                    }
                }
            }

            override fun onError(e: Throwable) {
                unknownErrorEvent.call()
            }
        }
        observeSingle(result, observer)
    }

    fun acceptApplication(id: Int) {
        val result = acceptApplicationUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> acceptApplicationSuccessEvent.setValue(id)
                    ResourceStatus.ERROR -> when (t.message) {
                        Error.UNAUTHORIZED -> {
                            tokenRefresh()
                            retryAcceptApplicationEvent.call()
                        }
                        Error.NOT_FOUND -> applicationNotFoundEvent.call()
                        else -> unknownErrorEvent.call()
                    }
                }
            }

            override fun onError(e: Throwable) {
                unknownErrorEvent.call()
            }
        }
        observeSingle(result, observer)
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