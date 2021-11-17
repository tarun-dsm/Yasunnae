package com.semicolon.yasunnae.ui.login

import androidx.activity.viewModels
import com.semicolon.domain.param.LoginParam
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(){

    override val layoutResId: Int
        get() = R.layout.activity_login

    private val loginViewModel: LoginViewModel by viewModels()

    override fun init() {
        binding.btnGotoRegister.setOnClickListener {
            startRegisterActivity()
        }
        binding.btnLoginAccount.setOnClickListener{
            val email = binding.etEmailBoxLogin.text
            val password = binding.etPasswordBoxLogin.text

            binding.tvLoginWarning.text = ""
            if(email.toString().isEmpty() || password.toString().isEmpty())
                binding.tvLoginWarning.text = getString(R.string.login_empty)
            else {
                val login = LoginParam(email.toString(), password.toString())
                loginViewModel.login(login)
                startMainActivity()
            }
        }
    }

    override fun observe() {
        loginViewModel.nonExistentEmailEvent.observe(this) {
            makeToast(getString(R.string.login_warning))
        }
    }

    private fun startMainActivity() {
        TODO("메인 Activity 실행")
    }

    private fun startRegisterActivity() {
        TODO("회원가입 Activity 실행")

    }
}
