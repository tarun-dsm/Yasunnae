package com.semicolon.yasunnae.ui.login

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.semicolon.domain.param.LoginParam
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.databinding.ActivityLoginBinding
import com.semicolon.yasunnae.ui.main.MainActivity
import com.semicolon.yasunnae.ui.main.MainActivity_GeneratedInjector
import com.semicolon.yasunnae.ui.registeraccount.RegisterAccountActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(){

    override val layoutResId: Int
        get() = R.layout.activity_login

    private val loginViewModel: LoginViewModel by viewModels()

    override fun init() {

        val email = binding.etEmailBoxLogin.text
        val password = binding.etPasswordBoxLogin.text

        binding.btnLoginAccount.setOnClickListener{
            binding.tvLoginWarning.text = ""
            if(email.toString().isEmpty() || password.toString().isEmpty()) {
                binding.tvLoginWarning.text = getString(R.string.login_empty)
                binding.tvLoginWarning.visibility = View.VISIBLE
            } else{
                val loginData = LoginParam(email.toString().trim(), password.toString().trim())
                loginViewModel.login(loginData)
            }
        }
        binding.gotoSignup.setOnClickListener {
            startRegisterActivity()
        }

    }

    override fun observe() {
        loginViewModel.successEvent.observe(this) {
            makeToast(getString(R.string.complete_login))
            startMainActivity()
        }
        loginViewModel.badRequestEvent.observe(this) {
            binding.tvLoginWarning.text = getString(R.string.login_warning)
        }
        loginViewModel.unknownErrorEvent.observe(this) {
            binding.tvLoginWarning.text = getString(R.string.non_existent_email)
        }
    }

    private fun startMainActivity() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    private fun startRegisterActivity() {
        val registerIntent = Intent(this, RegisterAccountActivity::class.java)
        startActivity(registerIntent)
    }
}