package com.semicolon.yasunnae.ui.postapplications

import android.content.Intent
import androidx.activity.viewModels
import com.semicolon.domain.entity.PostApplicationEntity
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.adapter.PostApplicationsAdapter
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.base.IntentKeys.KEY_APPLICATION_ID
import com.semicolon.yasunnae.base.IntentKeys.KEY_END_DATE
import com.semicolon.yasunnae.base.IntentKeys.KEY_POST_ID
import com.semicolon.yasunnae.base.IntentKeys.KEY_START_DATE
import com.semicolon.yasunnae.base.IntentKeys.KEY_USER_ID
import com.semicolon.yasunnae.databinding.ActivityPostApplicationsBinding
import com.semicolon.yasunnae.ui.login.LoginActivity
import com.semicolon.yasunnae.ui.profile.ProfileActivity
import com.semicolon.yasunnae.ui.review.WriteReviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostApplicationsActivity : BaseActivity<ActivityPostApplicationsBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_post_applications

    private val postApplicationsViewModel: PostApplicationsViewModel by viewModels()
    private var postId = 0

    override fun onResume() {
        super.onResume()
        postApplicationsViewModel.getPostApplication(postId)
    }

    override fun init() {
        postId = intent.getIntExtra(KEY_POST_ID, 0)
        val startDate = intent.getStringExtra(KEY_START_DATE)
        val endDate = intent.getStringExtra(KEY_END_DATE)
        binding.tvStartDatePostApplication.text = startDate
        binding.tvEndDatePostApplication.text = endDate
        binding.btnBackPostApplications.setOnClickListener {
            finish()
        }
        binding.btnGoToPost.setOnClickListener {
            finish()
        }
    }

    override fun observe() {
        postApplicationsViewModel.postApplicationsLiveData.observe(this) {
            var isDecided = false
            var acceptedApplicationId = 0
            it.map { application ->
                if (application.isAccepted) {
                    isDecided = true
                    acceptedApplicationId = application.applicationId
                }
            }
            setUpRecyclerView(isDecided, acceptedApplicationId, it)
        }
        postApplicationsViewModel.retryGetPostApplicationEvent.observe(this) {
            makeToast(getString(R.string.try_it_later))
            finish()
        }
        postApplicationsViewModel.needToLoginEvent.observe(this) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        postApplicationsViewModel.postNotFoundEvent.observe(this) {
            makeToast(getString(R.string.post_not_found))
            finish()
        }
        postApplicationsViewModel.unknownErrorEvent.observe(this) {
            makeToast(getString(R.string.unknown_error))
        }
        postApplicationsViewModel.acceptApplicationSuccessEvent.observe(this) {
            postApplicationsViewModel.getPostApplication(postId)
            makeToast(getString(R.string.accept_success))
        }
        postApplicationsViewModel.retryAcceptApplicationEvent.observe(this) {
            makeToast(getString(R.string.try_it_later))
        }
        postApplicationsViewModel.applicationNotFoundEvent.observe(this) {
            makeToast(getString(R.string.application_not_found))
        }
    }

    private fun setUpRecyclerView(
        isDecided: Boolean,
        acceptedApplicationId: Int,
        data: List<PostApplicationEntity>
    ) {
        val postApplicationsAdapter = PostApplicationsAdapter(
            this, isDecided, acceptedApplicationId,
            onItemClick = {
                val intent = Intent(this@PostApplicationsActivity, ProfileActivity::class.java)
                intent.putExtra(KEY_USER_ID, it)
                startActivity(intent)
            },
            onAcceptClick = {
                postApplicationsViewModel.acceptApplication(it)
            },
            onWriteReviewClick = {
                val intent = Intent(this, WriteReviewActivity::class.java)
                intent.putExtra(KEY_APPLICATION_ID, it)
                startActivity(intent)
            }
        )
        val list = ArrayList(data.filter { it.isAccepted })
        list.addAll(data.filter { !it.isAccepted })
        binding.rvPostApplicationList.adapter = postApplicationsAdapter
        postApplicationsAdapter.postApplications = list
    }
}