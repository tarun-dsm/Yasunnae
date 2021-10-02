package com.semicolon.yasunnae.ui.postlist

import androidx.lifecycle.MutableLiveData
import com.semicolon.domain.base.Error
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.entity.PostEntity
import com.semicolon.domain.entity.ProfileEntity
import com.semicolon.domain.usecase.auth.TokenRefreshUseCase
import com.semicolon.domain.usecase.post.GetPostListUseCase
import com.semicolon.domain.usecase.profile.GetProfileUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import io.reactivex.observers.DisposableSingleObserver

class PostListViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val getPostListUseCase: GetPostListUseCase,
    private val tokenRefreshUseCase: TokenRefreshUseCase
) : BaseViewModel() {

    val isLocationVerifiedLiveData = MutableLiveData<Boolean>()
    val postListLiveDate = MutableLiveData<List<PostEntity>>()
    val retryEvent = SingleLiveEvent<Unit>()
    val needToLoginEvent = SingleLiveEvent<Unit>()
    val unknownErrorEvent = SingleLiveEvent<Unit>()

    fun isLocationVerified() {
        val result = getProfileUseCase.interact(null)
        val observer = object : DisposableSingleObserver<Resource<ProfileEntity>>() {
            override fun onSuccess(t: Resource<ProfileEntity>) {
                when (t.status) {
                    ResourceStatus.SUCCESS ->
                        isLocationVerifiedLiveData.value = t.data!!.locationConfirm
                    ResourceStatus.ERROR -> when (t.message) {
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

    fun getPostList() {
        val result = getPostListUseCase.interact(Unit)
        val observer = object : DisposableSingleObserver<Resource<List<PostEntity>>>() {
            override fun onSuccess(t: Resource<List<PostEntity>>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> postListLiveDate.value = t.data!!
                    ResourceStatus.ERROR -> when (t.message) {
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