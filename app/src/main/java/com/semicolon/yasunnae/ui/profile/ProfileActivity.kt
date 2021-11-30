package com.semicolon.yasunnae.ui.profile

import androidx.activity.viewModels
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.base.IntentKeys.KEY_USER_ID
import com.semicolon.yasunnae.databinding.ActivityProfileBinding
import com.semicolon.yasunnae.dialog.NotifyDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_profile

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun init() {
        val fragment = ProfileFragment()
        val userId = intent.getIntExtra(KEY_USER_ID, 0)
        supportFragmentManager.beginTransaction().replace(
            binding.flProfile.id, fragment
        ).commit()
        ProfileFragment.IS_MINE = false
        ProfileFragment.USER_ID = userId

        binding.btnWriteReview.setOnClickListener {
            profileViewModel.hasInterested(userId)
        }
    }

    override fun observe() {
        profileViewModel.hasInterestedEvent.observe(this) {
            if (it) {
                // TODO("리뷰 작성 페이지 열기")
            } else {
                NotifyDialog(
                    this, getString(R.string.need_to_interested)
                ).callDialog()
            }
        }
    }
}