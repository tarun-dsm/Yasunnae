package com.semicolon.yasunnae.ui.postapplications

import androidx.activity.viewModels
import com.semicolon.domain.entity.PostApplicationEntity
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.adapter.PostApplicationsAdapter
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.base.IntentKeys.KEY_END_DATE
import com.semicolon.yasunnae.base.IntentKeys.KEY_POST_ID
import com.semicolon.yasunnae.base.IntentKeys.KEY_START_DATE
import com.semicolon.yasunnae.databinding.ActivityPostApplicationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostApplicationsActivity : BaseActivity<ActivityPostApplicationsBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_post_applications

    private val postApplicationsViewModel: PostApplicationsViewModel by viewModels()
    private val onItemClickListener = object : PostApplicationsAdapter.OnItemClickListener {
        override fun onItemClick(applicantId: Int) {
//            TODO("프로필 페이지로 이동하는 코드 작성")
        }
    }
    private val onAcceptClickListener = object : PostApplicationsAdapter.OnAcceptClickListener {
        override fun onAcceptClick(applicationId: Int) {
            postApplicationsViewModel.acceptApplication(applicationId)
        }
    }
    private var postId = 0

    override fun init() {
        postId = intent.getIntExtra(KEY_POST_ID, 0)
        val startDate = intent.getStringExtra(KEY_START_DATE)
        val endDate = intent.getStringExtra(KEY_END_DATE)
        binding.tvStartDatePostApplication.text = startDate
        binding.tvEndDatePostApplication.text = endDate
        binding.btnGoToPost.setOnClickListener {
            finish()
        }
        postApplicationsViewModel.getPostApplication(postId)
    }

    override fun observe() {
        postApplicationsViewModel.postApplicationsLiveData.observe(this) {
            var isDecided = false
            it.map { application -> if (application.isAccepted) isDecided = true }
            setUpRecyclerView(isDecided, it)
        }
        postApplicationsViewModel.retryGetPostApplicationEvent.observe(this) {
            makeToast(getString(R.string.try_it_later))
            finish()
        }
        postApplicationsViewModel.needToLoginEvent.observe(this) {
            TODO("로그인 창 열기")
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
        data: List<PostApplicationEntity>
    ) {
        val postApplicationsAdapter = PostApplicationsAdapter(
            this, isDecided, onItemClickListener, onAcceptClickListener
        )
        binding.rvPostApplicationList.adapter = postApplicationsAdapter
        postApplicationsAdapter.postApplications = ArrayList(data)
    }
}