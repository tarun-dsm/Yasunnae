package com.semicolon.yasunnae.ui.login
import com.semicolon.domain.base.Error
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.LoginParam
import com.semicolon.domain.usecase.auth.LoginUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel() {

    val badRequestEvent = SingleLiveEvent<Unit>()
    val unknownErrorEvent = SingleLiveEvent<Unit>()
    val successEvent = SingleLiveEvent<Unit>()

    fun login(login: LoginParam) {
        val result = loginUseCase.interact(login)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> successEvent.call()

                    ResourceStatus.ERROR -> when (t.message) {
                        Error.BAD_REQUEST -> {
                            badRequestEvent.call()
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
}