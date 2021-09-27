package com.semicolon.yasunnae.ui.coordinate

import com.semicolon.domain.base.Error
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.param.CoordinateParam
import com.semicolon.domain.usecase.account.SaveCoordinateUseCase
import com.semicolon.domain.usecase.auth.TokenRefreshUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class CoordinateViewModel @Inject constructor(
    private val saveCoordinateUseCase: SaveCoordinateUseCase,
    private val tokenRefreshUseCase: TokenRefreshUseCase
) : BaseViewModel() {

    val successEvent = SingleLiveEvent<Unit>()
    val badRequestEvent = SingleLiveEvent<Unit>()
    val retryEvent = SingleLiveEvent<Unit>()
    val needToLoginEvent = SingleLiveEvent<Unit>()
    val unknownErrorEvent = SingleLiveEvent<Unit>()

    fun saveCoordinate(longitude: Double, latitude: Double) {
        val result = saveCoordinateUseCase.interact(
            CoordinateParam(longitude, latitude)
        )
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> successEvent.call()
                    ResourceStatus.ERROR -> when (t.message!!) {
                        Error.BAD_REQUEST -> badRequestEvent.call()
                        Error.UNAUTHORIZED -> {
                            tokenRefresh()
                            retryEvent.call()
                        }
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