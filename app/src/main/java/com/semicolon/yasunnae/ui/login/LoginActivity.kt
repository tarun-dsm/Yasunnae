package com.semicolon.yasunnae.ui.login

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import com.semicolon.domain.param.LoginParam
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.databinding.ActivityLoginBinding
import com.semicolon.yasunnae.dialog.BlockedUserDialog
import com.semicolon.yasunnae.dialog.NeedToVerifyLocationDialog
import com.semicolon.yasunnae.ui.main.MainActivity
import com.semicolon.yasunnae.ui.registeraccount.RegisterAccountActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_login

    private val loginViewModel: LoginViewModel by viewModels()

    override fun init() {

        val email = binding.etEmailBoxLogin.text
        val password = binding.etPasswordBoxLogin.text

        binding.btnLoginAccount.setOnClickListener {
            binding.tvLoginWarning.text = ""
            if (email.toString().isEmpty() || password.toString().isEmpty()) {
                binding.tvLoginWarning.text = getString(R.string.login_empty)
                binding.tvLoginWarning.visibility = View.VISIBLE
            } else {
                val loginData = LoginParam(email.toString().trim(), password.toString().trim())
                loginViewModel.login(loginData)
            }
        }
        binding.gotoSignup.setOnClickListener {
            startRegisterActivity()
        }
        loginViewModel.tokenRefresh()
    }

    override fun observe() {
        loginViewModel.successEvent.observe(this) {
            binding.tvLoginWarning.visibility = View.INVISIBLE
            makeToast(getString(R.string.complete_login))
            startMainActivity()
        }
        loginViewModel.badRequestEvent.observe(this) {
            binding.tvLoginWarning.text = getString(R.string.login_warning)
            binding.tvLoginWarning.visibility = View.VISIBLE
        }
        loginViewModel.blockedUserEvent.observe(this) {
            BlockedUserDialog(context = this).callDialog()
        }
        loginViewModel.unknownErrorEvent.observe(this) {
            binding.tvLoginWarning.text = getString(R.string.non_existent_email)
            binding.tvLoginWarning.visibility = View.VISIBLE
        }
        loginViewModel.tokenRefreshSuccessEvent.observe(this) {
            startMainActivity()
        }
        loginViewModel.tokenRefreshSuccessEvent.observe(this) {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun startRegisterActivity() {
        val registerIntent = Intent(this, RegisterAccountActivity::class.java)
        startActivity(registerIntent)
        finish()
    }
}