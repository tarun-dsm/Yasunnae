package com.semicolon.yasunnae.ui.profile

import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.base.IntentKeys.KEY_USER_ID
import com.semicolon.yasunnae.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_profile

    private var userId = 0

    override fun init() {
        val fragment = ProfileFragment()
        userId = intent.getIntExtra(KEY_USER_ID, 0)

        supportFragmentManager.beginTransaction().replace(
            binding.flProfile.id, fragment
        ).commit()
        ProfileFragment.IS_MINE = false
        ProfileFragment.USER_ID = userId
    }

    override fun observe() {}
}