package com.semicolon.yasunnae.ui.postdetail

import androidx.lifecycle.MutableLiveData
import com.semicolon.domain.base.Error
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.entity.PostDetailEntity
import com.semicolon.domain.entity.ProfileEntity
import com.semicolon.domain.usecase.application.CancelApplicationUseCase
import com.semicolon.domain.usecase.application.SendApplicationUseCase
import com.semicolon.domain.usecase.auth.TokenRefreshUseCase
import com.semicolon.domain.usecase.post.DeletePostUseCase
import com.semicolon.domain.usecase.post.GetPostDetailUseCase
import com.semicolon.domain.usecase.profile.GetProfileUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val getPostDetailUseCase: GetPostDetailUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val sendApplicationUseCase: SendApplicationUseCase,
    private val cancelApplicationUseCase: CancelApplicationUseCase,
    private val tokenRefreshUseCase: TokenRefreshUseCase
) : BaseViewModel() {

    val postDetailLiveData = MutableLiveData<PostDetailEntity>()
    val profileLiveData = MutableLiveData<ProfileEntity>()
    val deletePostSuccessEvent = SingleLiveEvent<Unit>()
    val sendApplicationSuccessEvent = SingleLiveEvent<Unit>()
    val cancelApplicationSuccessEvent = SingleLiveEvent<Unit>()
    val postDetailBadRequestEvent = SingleLiveEvent<Unit>()
    val sendApplicationBadRequestEvent = SingleLiveEvent<Unit>()
    val retryEvent = SingleLiveEvent<Unit>()
    val needToLoginEvent = SingleLiveEvent<Unit>()
    val postNotFoundEvent = SingleLiveEvent<Unit>()
    val profileNotFoundEvent = SingleLiveEvent<Unit>()
    val conflictEvent = SingleLiveEvent<Unit>()
    val applicationConflictEvent = SingleLiveEvent<Unit>()
    val unknownErrorEvent = SingleLiveEvent<Unit>()

    fun getPostDetail(id: Int) {
        val result = getPostDetailUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<PostDetailEntity>>() {
            override fun onSuccess(t: Resource<PostDetailEntity>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> postDetailLiveData.value = t.data!!
                    ResourceStatus.ERROR -> when (t.message) {
                        Error.BAD_REQUEST -> postDetailBadRequestEvent.call()
                        Error.UNAUTHORIZED -> {
                            tokenRefresh()
                            retryEvent.call()
                        }
                        Error.NOT_FOUND -> postNotFoundEvent.call()
                        Error.CONFLICT -> conflictEvent.call()
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

    fun deletePost(id: Int) {
        val result = deletePostUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> deletePostSuccessEvent.call()
                    ResourceStatus.ERROR -> when (t.message) {
                        Error.UNAUTHORIZED -> {
                            tokenRefresh()
                            retryEvent.call()
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

    fun getProfile(id: Int?) {
        val result = getProfileUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<ProfileEntity>>() {
            override fun onSuccess(t: Resource<ProfileEntity>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> profileLiveData.value = t.data!!
                    ResourceStatus.ERROR -> when (t.message) {
                        Error.UNAUTHORIZED -> {
                            tokenRefresh()
                            retryEvent.call()
                        }
                        Error.NOT_FOUND -> profileNotFoundEvent.call()
                        else -> unknownErrorEvent.call()
                    }
                }
            }

            override fun onError(e: Throwable) {
                unknownErrorEvent
            }
        }
        observeSingle(result, observer)
    }

    fun sendApplication(id: Int) {
        val result = sendApplicationUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> sendApplicationSuccessEvent.call()
                    ResourceStatus.ERROR -> when (t.message) {
                        Error.BAD_REQUEST -> sendApplicationBadRequestEvent.call()
                        Error.UNAUTHORIZED -> {
                            tokenRefresh()
                            retryEvent.call()
                        }
                        Error.NOT_FOUND -> postNotFoundEvent.call()
                        Error.CONFLICT -> applicationConflictEvent.call()
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

    fun cancelApplication(id: Int) {
        val result = cancelApplicationUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> cancelApplicationSuccessEvent.call()
                    ResourceStatus.ERROR -> when (t.message) {
                        Error.UNAUTHORIZED -> {
                            tokenRefresh()
                            retryEvent.call()
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