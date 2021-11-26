package com.semicolon.yasunnae.ui.registeraccount

import android.util.Log
import android.widget.Toast
import com.semicolon.domain.base.Error
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.ResourceStatus
import com.semicolon.domain.param.EmailCertificationParam
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.param.ReportParam
import com.semicolon.domain.usecase.account.*
import com.semicolon.domain.usecase.email.CheckCertificationNumberUseCase
import com.semicolon.domain.usecase.email.SendCertificationMailUseCase
import com.semicolon.yasunnae.base.BaseViewModel
import com.semicolon.yasunnae.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class RegisterAccountViewModel @Inject constructor(
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val checkEmailDuplication: CheckEmailDuplicationUseCase,
    private val certificationNumberUseCase: CheckCertificationNumberUseCase,
    private val sendCertificationMailUseCase: SendCertificationMailUseCase,
    private val checkNicknameDuplicationUseCase: CheckNicknameDuplicationUseCase,
) : BaseViewModel(){

    val badRequestEvent = SingleLiveEvent<Unit>()
    val unknownErrorEvent = SingleLiveEvent<Unit>()
    val successEvent = SingleLiveEvent<Unit>()

    // email
    val checkEmailDuplicateSuccessEvent = SingleLiveEvent<Unit>()
    val checkEmailDuplicateBadRequestEvent = SingleLiveEvent<Unit>()
    val DuplicateEmailEvent = SingleLiveEvent<Unit>()
    val needToVerifyEmailEvent = SingleLiveEvent<Unit>()

    val certificationEmailSuccessEvent = SingleLiveEvent<Unit>()
    val certificationEmailBadRequestEvent = SingleLiveEvent<Unit>()

    val certificationNumberSuccessEvent = SingleLiveEvent<Unit>()
    val certificationNumberBadRequestEvent = SingleLiveEvent<Unit>()

    // nickname
    val nicknameSuccessEvent = SingleLiveEvent<Unit>()
    val DuplicateNicknameEvent = SingleLiveEvent<Unit>()

    fun emailDuplication(email:String?) {
        val result = checkEmailDuplication.interact(email)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> checkEmailDuplicateSuccessEvent.call()

                    ResourceStatus.ERROR -> when (t.message) {
                        Error.BAD_REQUEST -> {
                            checkEmailDuplicateBadRequestEvent.call()
                        }
                        Error.CONFLICT -> {
                            DuplicateEmailEvent.call()          // 중복되는 이메일
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

    fun sendCertification(email: String?) {
        val result = sendCertificationMailUseCase.interact(email)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> certificationEmailSuccessEvent.call()

                    ResourceStatus.ERROR -> when (t.message) {
                        Error.BAD_REQUEST -> {
                            certificationEmailBadRequestEvent.call()        // 잘못된 이메일 형식 OR 전송이 제대로 이뤄지지 않은 경우
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

    fun certificationNumber(email: EmailCertificationParam?) {
        val result = certificationNumberUseCase.interact(email)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when (t.status) {
                    ResourceStatus.SUCCESS -> certificationNumberSuccessEvent.call()


                    ResourceStatus.ERROR -> when (t.message) {
                        Error.BAD_REQUEST -> {
                            certificationNumberBadRequestEvent.call()           // 일치하지 않는 인증번호
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

    fun nicknameDuplication(nickname:String?) {
        val result = checkNicknameDuplicationUseCase.interact(nickname)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when(t.status) {
                    ResourceStatus.SUCCESS -> nicknameSuccessEvent.call()

                    ResourceStatus.ERROR -> when(t.message) {
                        Error.CONFLICT -> {
                            DuplicateNicknameEvent.call()       // 중복되는 닉네임
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

    fun register(register: RegisterAccountParam) {
        val result = registerAccountUseCase.interact(register)
        val observer = object : DisposableSingleObserver<Resource<Unit>>() {
            override fun onSuccess(t: Resource<Unit>) {
                when(t.status) {
                    ResourceStatus.SUCCESS -> successEvent.call()

                    ResourceStatus.ERROR -> when(t.message) {
                        Error.BAD_REQUEST -> {
                            badRequestEvent.call()      // 조건에 맞지 않는 요청
                        }
                        Error.UNAUTHORIZED -> {
                            needToVerifyEmailEvent.call()    // email 인증 필요
                        }
                        Error.CONFLICT -> {
                            DuplicateEmailEvent.call()  // 이미 있는 이메일
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