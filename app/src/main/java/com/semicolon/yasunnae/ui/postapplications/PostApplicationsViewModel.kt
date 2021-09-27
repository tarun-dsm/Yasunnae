package com.semicolon.yasunnae.ui.postapplications

import androidx.lifecycle.MutableLiveData
import com.semicolon.domain.base.Error
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.entity.PostApplicationEntity
import com.semicolon.domain.usecase.auth.TokenRefreshUseCase
import com.semicolon.domain.usecase.post.GetPostApplicationUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class PostApplicationsViewModel @Inject constructor(
    private val getPostApplicationUseCase: GetPostApplicationUseCase,
    private val tokenRefreshUseCase: TokenRefreshUseCase
) : BaseViewModel() {

    val postApplicationsLiveData = MutableLiveData<List<PostApplicationEntity>>()
    val badRequestEvent = SingleLiveEvent<Unit>()
    val retryEvent = SingleLiveEvent<Unit>()
    val needToLoginEvent = SingleLiveEvent<Unit>()
    val notFoundEvent = SingleLiveEvent<Unit>()
    val unknownErrorEvent = SingleLiveEvent<Unit>()

    fun getPostApplication(id: Int) {
        val result = getPostApplicationUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<List<PostApplicationEntity>>>() {
            override fun onSuccess(t: Resource<List<PostApplicationEntity>>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> postApplicationsLiveData.value = t.data!!
                    ResourceStatus.ERROR -> when (t.message) {
                        Error.BAD_REQUEST -> badRequestEvent.call()
                        Error.UNAUTHORIZED -> {
                            tokenRefresh()
                            retryEvent.call()
                        }
                        Error.NOT_FOUND -> notFoundEvent.call()
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