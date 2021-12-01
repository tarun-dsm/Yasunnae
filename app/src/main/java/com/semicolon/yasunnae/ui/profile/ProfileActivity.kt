package com.semicolon.yasunnae.ui.profile

import android.content.Intent
import androidx.activity.viewModels
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.base.IntentKeys.KEY_USER_ID
import com.semicolon.yasunnae.base.IntentKeys.KEY_USER_NAME
import com.semicolon.yasunnae.databinding.ActivityProfileBinding
import com.semicolon.yasunnae.dialog.NotifyDialog
import com.semicolon.yasunnae.ui.review.WriteReviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_profile

    private var userId = 0

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun init() {
        val fragment = ProfileFragment()
        val userNickname = intent.getStringExtra(KEY_USER_NAME)
        val message = userNickname + getString(R.string.please_write_review)
        userId = intent.getIntExtra(KEY_USER_ID, 0)

        supportFragmentManager.beginTransaction().replace(
            binding.flProfile.id, fragment
        ).commit()
        ProfileFragment.IS_MINE = false
        ProfileFragment.USER_ID = userId
        binding.tvPleaseWriteReview.text = message
        binding.btnWriteReview.setOnClickListener {
            profileViewModel.hasInterested(userId)
        }
    }

    override fun observe() {
        profileViewModel.hasInterestedEvent.observe(this) {
            if (it) {
                val intent = Intent(this, WriteReviewActivity::class.java)
                intent.putExtra(KEY_USER_ID, userId)
                startActivity(intent)
            } else {
                NotifyDialog(
                    this, getString(R.string.need_to_interested)
                ).callDialog()
            }
        }
    }
}