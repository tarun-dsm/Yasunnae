package com.semicolon.yasunnae.ui.postdetail

import androidx.lifecycle.MutableLiveData
import com.semicolon.domain.base.Error
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.entity.CommentEntity
import com.semicolon.domain.param.CommentParam
import com.semicolon.domain.usecase.auth.TokenRefreshUseCase
import com.semicolon.domain.usecase.comment.AddCommentUseCase
import com.semicolon.domain.usecase.comment.DeleteCommentUseCase
import com.semicolon.domain.usecase.comment.FixCommentUseCase
import com.semicolon.domain.usecase.comment.GetCommentListUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val getCommentListUseCase: GetCommentListUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val fixCommentUseCase: FixCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val tokenRefreshUseCase: TokenRefreshUseCase
) : BaseViewModel() {

    val commentListLiveData = MutableLiveData<List<CommentEntity>>()
    val addCommentSuccessEvent = SingleLiveEvent<Unit>()
    val fixCommentSuccessEvent = SingleLiveEvent<Unit>()
    val deleteCommentSuccessEvent = SingleLiveEvent<Unit>()
    val commentBadRequestEvent = SingleLiveEvent<Unit>()
    val retryEvent = SingleLiveEvent<Unit>()
    val postNotFoundEvent = SingleLiveEvent<Unit>()
    val commentNotFoundEvent = SingleLiveEvent<Unit>()
    val needToLoginEvent = SingleLiveEvent<Unit>()
    val unknownErrorEvent = SingleLiveEvent<Unit>()

    fun getCommentList(id: Int) {
        val result = getCommentListUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<List<CommentEntity>>>() {
            override fun onSuccess(t: Resource<List<CommentEntity>>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> commentListLiveData.value = t.data!!
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

    fun addComment(comment: CommentParam) {
        val result = addCommentUseCase.interact(comment)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> addCommentSuccessEvent.call()
                    ResourceStatus.ERROR -> when (t.message) {
                        Error.BAD_REQUEST -> commentBadRequestEvent.call()
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

    fun fixComment(comment: CommentParam) {
        val result = fixCommentUseCase.interact(comment)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> fixCommentSuccessEvent.call()
                    ResourceStatus.ERROR -> when (t.message) {
                        Error.BAD_REQUEST -> commentBadRequestEvent.call()
                        Error.UNAUTHORIZED -> {
                            tokenRefresh()
                            retryEvent.call()
                        }
                        Error.NOT_FOUND -> commentNotFoundEvent.call()
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

    fun deleteComment(id: Int) {
        val result = deleteCommentUseCase.interact(id)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> deleteCommentSuccessEvent.call()
                    ResourceStatus.ERROR -> when (t.message) {
                        Error.UNAUTHORIZED -> {
                            tokenRefresh()
                            retryEvent.call()
                        }
                        Error.NOT_FOUND -> commentNotFoundEvent.call()
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