package com.semicolon.yasunnae.ui.registeraccount

import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.semicolon.domain.param.EmailCertificationParam
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.param.Sex
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.databinding.ActivityRegisterAccountBinding
import com.semicolon.yasunnae.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class RegisterAccountActivity : BaseActivity<ActivityRegisterAccountBinding>() {
    override val layoutResId: Int
        get() = R.layout.activity_register_account

    private val registerViewModel : RegisterAccountViewModel by viewModels()

    var isVerified = false
    var isVerifiedPassword = false
    var isVerifiedNickname = false

    override fun init() {

        // text count
        binding.etExperienceRegisterAccount.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tvCountText.text = "0 / 100"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userinput = binding.etExperienceRegisterAccount.text.toString()
                binding.tvCountText.text = userinput.length.toString() + " / 100"
            }

            override fun afterTextChanged(s: Editable?) {
                var userinput = binding.etExperienceRegisterAccount.text.toString()
                binding.tvCountText.text = userinput.length.toString() + " / 100"
            }

        })

        // email 중복
        binding.btnSendEmail.setOnClickListener {
            val email = binding.etEmailRegisterAccount.text.toString().trim()  // 이메일 중복 확인
            registerViewModel.emailDuplication(email)
        }

        // 인증번호
        binding.btnEmailVerifyRegisterAccount.setOnClickListener {
            val email = binding.etEmailRegisterAccount.text.toString()
            val number = binding.etEmailVerifyRegisterAccount.text.toString()
            registerViewModel.certificationNumber(EmailCertificationParam(email, number))
        }

        // password
        binding.btnCheckPasswordRegisterAccount.setOnClickListener {

            val checkPassword = binding.etCheckPasswordRegisterAccount.text.toString()
            val password = binding.etPasswordRegisterAccount.text.toString()

            if(Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}$", password)) {
                binding.tvDifferentPasswordWarning.visibility = View.INVISIBLE
                if (checkPassword == password) {
                    isVerifiedPassword = true
                    makeToast(getString(R.string.ok_ok))

                } else {
                    binding.tvDifferentPasswordWarning.text = getString(R.string.different_password)
                    binding.tvDifferentPasswordWarning.visibility = View.VISIBLE
                }
            }
            else {
                binding.tvDifferentPasswordWarning.text = getString(R.string.wrong_password_format)
                binding.tvDifferentPasswordWarning.visibility = View.VISIBLE
            }
        }

        // nickname
        binding.btnCheckDuplicateNickname.setOnClickListener {
            registerViewModel.nicknameDuplication(binding.etNicknameRegisterAccount.text.toString())
        }

        binding.ivBackArrowRegisterAccount.setOnClickListener {
            finish()
        }

        // register
        binding.btnRegisterAccount.setOnClickListener {

            if(binding.etEmailRegisterAccount.text.isEmpty() || binding.etPasswordRegisterAccount.text.isEmpty() ||
                binding.etNicknameRegisterAccount.text.isEmpty() || binding.etAgeRegisterAccount.text.isEmpty()) {
                makeToast(getString(R.string.check_things))
                if(binding.etAgeRegisterAccount.text.isEmpty()) {
                    binding.tvPleaseInputAgeWarning.visibility = View.VISIBLE
                } else {
                    binding.tvPleaseInputAgeWarning.visibility = View.INVISIBLE
                }
            }

            else {
                val email = binding.etEmailRegisterAccount.text.toString().trim()
                val password = binding.etPasswordRegisterAccount.text.toString().trim()
                val nickname = binding.etNicknameRegisterAccount.text.toString()

                val age = binding.etAgeRegisterAccount.text.toString().toInt()

                var sex = Sex.MALE
                var raised = true
                val experience = binding.etExperienceRegisterAccount.text.toString()

                if (binding.rbFemaleRegisterAccount.isChecked) {
                    sex = Sex.FEMALE
                }

                if(binding.rbNoRegisterAccount.isChecked) {
                    raised = false
                }

                if(isVerified && isVerifiedPassword && isVerifiedNickname) {
                    val registerData = RegisterAccountParam(email, password, nickname, age, sex, raised, experience)
                    registerViewModel.register(registerData)
                } else {
                    when {
                        !isVerified -> makeToast(getString(R.string.need_to_verify_email))
                        !isVerifiedPassword -> makeToast(getString(R.string.need_to_check_password))
                        !isVerifiedNickname -> makeToast(getString(R.string.need_to_check_nickname))
                    }
                }
            }
        }
     }

    override fun observe() {

        // successEvent
        // complete register
        registerViewModel.successEvent.observe(this) {
            makeToast(getString(R.string.complete_register_account))
            startLoginActivity()
        }

        // email
        registerViewModel.checkEmailDuplicateSuccessEvent.observe(this) { // 아메일 중복 인증 성공
            val email = binding.etEmailRegisterAccount.text.toString()
            binding.tvDuplicateEmailWarning.visibility = View.INVISIBLE
            registerViewModel.sendCertification(email)
        }

        registerViewModel.certificationEmailSuccessEvent.observe(this) {      // 이메일 전송 성공
            makeToast(getString(R.string.ok_send_email))
        }

        registerViewModel.certificationNumberSuccessEvent.observe(this) {   // 인증 성공
            binding.tvIncorrectVerifyNumberWarning.visibility = View.INVISIBLE
            isVerified = true
            makeToast(getString(R.string.ok_ok))                // 확인되었습니다.
        }

        // nickname
        registerViewModel.nicknameSuccessEvent.observe(this) { // 닉네임 중복 확인 성공
            binding.tvDuplicateNicknameWarning.visibility = View.INVISIBLE
            isVerifiedNickname = true
            makeToast(getString(R.string.ok_nickname))
        }


        // Bad...ㅠㅠ

        // register
        registerViewModel.badRequestEvent.observe(this) {
            makeToast(getString(R.string.check_things))
        }

        registerViewModel.needToVerifyEmailEvent.observe(this) {
            makeToast(getString(R.string.need_to_verify_email))     // 이메일 인증이 필요
        }

        // email
        registerViewModel.checkEmailDuplicateBadRequestEvent.observe(this) {
            binding.tvDuplicateEmailWarning.text = getString(R.string.non_email_form)   // 잘못된 이메일 형식
            binding.tvDuplicateEmailWarning.visibility = View.VISIBLE
        }

        registerViewModel.DuplicateEmailEvent.observe(this) {    // 이메일 중복
            binding.tvDuplicateEmailWarning.text = getString(R.string.duplicate_email)
            binding.tvDuplicateEmailWarning.visibility = View.VISIBLE      // 중복되는 이메일
        }

        registerViewModel.certificationEmailBadRequestEvent.observe(this) {
            binding.tvDuplicateEmailWarning.text = getString(R.string.send_failed)      // 전송 실패
            binding.tvDuplicateEmailWarning.visibility = View.VISIBLE
        }

        registerViewModel.certificationNumberBadRequestEvent.observe(this) {
            binding.tvIncorrectVerifyNumberWarning.visibility = View.VISIBLE      // 인증번호 올바르지 않음
        }


        // nickname

        registerViewModel.DuplicateNicknameEvent.observe(this) {   // 닉네임 bad request
            binding.tvDuplicateNicknameWarning.visibility = View.VISIBLE       // 중복되는 닉네임입니다.
        }


        registerViewModel.unknownErrorEvent.observe(this) {
            makeToast(getString(R.string.unknown_error))
        }

    }

    private fun startLoginActivity() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
    }
}