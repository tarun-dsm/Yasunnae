package com.semicolon.yasunnae.ui.applications

import androidx.lifecycle.MutableLiveData
import com.semicolon.domain.base.Error
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.entity.ApplicationEntity
import com.semicolon.domain.entity.ProfileEntity
import com.semicolon.domain.usecase.application.GetMyApplicationListUseCase
import com.semicolon.domain.usecase.auth.TokenRefreshUseCase
import com.semicolon.domain.usecase.profile.GetProfileUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class MyApplicationsViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getPostListUseCase: GetMyApplicationListUseCase,
    private val tokenRefreshUseCase: TokenRefreshUseCase
) : BaseViewModel() {

    val isLocationVerifiedLiveData = SingleLiveEvent<Boolean>()
    val userLocationLiveData = MutableLiveData<String>()
    val postListLiveDate = MutableLiveData<List<ApplicationEntity>>()
    val retryEvent = SingleLiveEvent<Unit>()
    val needToLoginEvent = SingleLiveEvent<Unit>()
    val unknownErrorEvent = SingleLiveEvent<Unit>()

    fun isLocationVerified() {
        val result = getProfileUseCase.interact(null)
        val observer = object : DisposableSingleObserver<Resource<ProfileEntity>>() {
            override fun onSuccess(t: Resource<ProfileEntity>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> {
                        isLocationVerifiedLiveData.setValue(t.data!!.locationConfirm)
                        userLocationLiveData.value = t.data!!.administrationDivision
                    }
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
        val observer = object : DisposableSingleObserver<Resource<List<ApplicationEntity>>>() {
            override fun onSuccess(t: Resource<List<ApplicationEntity>>) {
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