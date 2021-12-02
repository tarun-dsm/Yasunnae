package com.semicolon.yasunnae.ui.profile

import androidx.lifecycle.MutableLiveData
import com.semicolon.domain.base.Error
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.entity.InterestedEntity
import com.semicolon.domain.entity.ProfileEntity
import com.semicolon.domain.entity.ProfilePostEntity
import com.semicolon.domain.entity.ReviewEntity
import com.semicolon.domain.param.ReportParam
import com.semicolon.domain.usecase.account.HasInterestedUseCase
import com.semicolon.domain.usecase.account.ReportUserUseCase
import com.semicolon.domain.usecase.auth.LogoutUseCase
import com.semicolon.domain.usecase.auth.TokenRefreshUseCase
import com.semicolon.domain.usecase.profile.GetProfilePostUseCase
import com.semicolon.domain.usecase.profile.GetProfileUseCase
import com.semicolon.domain.usecase.profile.GetReviewUseCase
import com.semicolon.domain.usecase.review.DeleteReviewUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getReviewUseCase: GetReviewUseCase,
    private val getProfilePostUseCase: GetProfilePostUseCase,
    private val reportUserUseCase: ReportUserUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val tokenRefreshUseCase: TokenRefreshUseCase
) : BaseViewModel() {

    val profileLiveData = MutableLiveData<ProfileEntity>()
    val reviewLiveData = MutableLiveData<List<ReviewEntity>>()
    val profilePostLiveData = MutableLiveData<List<ProfilePostEntity>>()
    val reportSuccessEvent = SingleLiveEvent<Unit>()
    val deleteReviewSuccessEvent = SingleLiveEvent<Unit>()
    val logoutSuccessEvent = SingleLiveEvent<Unit>()
    val retryEvent = SingleLiveEvent<Unit>()
    val needToLoginEvent = SingleLiveEvent<Unit>()
    val unknownErrorEvent = SingleLiveEvent<Unit>()

    fun getProfile(id: Int?) {
        val result = getProfileUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<ProfileEntity>>() {
            override fun onSuccess(t: Resource<ProfileEntity>) {
                if (t.status == ResourceStatus.SUCCESS)
                    profileLiveData.value = t.data!!
                else if (t.message == Error.UNAUTHORIZED) {
                    retryEvent.call()
                    tokenRefresh()
                }
            }

            override fun onError(e: Throwable) {
                unknownErrorEvent.call()
            }
        }
        observeSingle(result, observer)
    }

    fun getReview(id: Int?) {
        val result = getReviewUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<List<ReviewEntity>>>() {
            override fun onSuccess(t: Resource<List<ReviewEntity>>) {
                if (t.status == ResourceStatus.SUCCESS)
                    reviewLiveData.value = t.data!!
                else if (t.message == Error.UNAUTHORIZED) {
                    retryEvent.call()
                    tokenRefresh()
                }
            }

            override fun onError(e: Throwable) {
                unknownErrorEvent.call()
            }
        }
        observeSingle(result, observer)
    }

    fun getProfilePost(id: Int?) {
        val result = getProfilePostUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<List<ProfilePostEntity>>>() {
            override fun onSuccess(t: Resource<List<ProfilePostEntity>>) {
                if (t.status == ResourceStatus.SUCCESS)
                    profilePostLiveData.value = t.data!!
                else if (t.message == Error.UNAUTHORIZED) {
                    retryEvent.call()
                    tokenRefresh()
                }
            }

            override fun onError(e: Throwable) {
                unknownErrorEvent.call()
            }
        }
        observeSingle(result, observer)
    }

    fun reportUser(reportParam: ReportParam) {
        val result = reportUserUseCase.interact(reportParam)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                if (t.status == ResourceStatus.SUCCESS)
                    reportSuccessEvent.call()
                else if (t.message == Error.UNAUTHORIZED) {
                    retryEvent.call()
                    tokenRefresh()
                }
            }

            override fun onError(e: Throwable) {
                unknownErrorEvent.call()
            }
        }
        observeSingle(result, observer)
    }

    fun deleteReview(id: Int) {
        val result = deleteReviewUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                if (t.status == ResourceStatus.SUCCESS)
                    deleteReviewSuccessEvent.call()
                else if (t.message == Error.UNAUTHORIZED) {
                    retryEvent.call()
                    tokenRefresh()
                }
            }

            override fun onError(e: Throwable) {
                unknownErrorEvent.call()
            }
        }
        observeSingle(result, observer)
    }

    fun logout() {
        val result = logoutUseCase.interact(Unit)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                logoutSuccessEvent.call()
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