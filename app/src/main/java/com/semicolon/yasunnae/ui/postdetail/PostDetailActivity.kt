package com.semicolon.yasunnae.ui.postdetail

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import com.semicolon.domain.entity.PostDetailEntity
import com.semicolon.domain.param.CommentParam
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.adapter.CommentsAdapter
import com.semicolon.yasunnae.adapter.PostDetailImageAdapter
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.base.IntentKeys.KEY_END_DATE
import com.semicolon.yasunnae.base.IntentKeys.KEY_POST_ID
import com.semicolon.yasunnae.base.IntentKeys.KEY_START_DATE
import com.semicolon.yasunnae.databinding.ActivityPostDetailBinding
import com.semicolon.yasunnae.dialog.EditCommentDialog
import com.semicolon.yasunnae.ui.postapplications.PostApplicationsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_post_detail

    private val postDetailViewModel: PostDetailViewModel by viewModels()
    private val commentViewModel: CommentViewModel by viewModels()
    private val postId = intent.getIntExtra(KEY_POST_ID, 0)

    private val postDetailImageAdapter = PostDetailImageAdapter()
    private val commentsAdapter = CommentsAdapter(
        onEditClick = {
            EditCommentDialog(this, it.comment) { fixedComment ->
                commentViewModel.fixComment(CommentParam(it.id, fixedComment))
            }.callDialog()
        },
        onDeleteClick = { commentViewModel.deleteComment(it.id) }
    )

    override fun init() {
        binding.vpImagePostDetail.adapter = postDetailImageAdapter
        binding.rvCommentsPostDetail.adapter = commentsAdapter
        binding.btnBack.setOnClickListener { finish() }
        binding.btnSendComment.setOnClickListener {
            val comment = binding.etCommentPostDetail.text.toString()
            if (comment.isNotEmpty()) commentViewModel.addComment(CommentParam(postId, comment))
        }
        postDetailViewModel.getPostDetail(postId)
        commentViewModel.getCommentList(postId)
    }

    override fun observe() {
        observePostDetailViewModel()
        observeCommentViewModel()
    }

    private fun observePostDetailViewModel() {
        val owner: LifecycleOwner = this
        postDetailViewModel.apply {
            postDetailLiveData.observe(owner) {
                val deadline = getString(R.string.deadline_colon) + " " + it.post.applicationEndDate
                val contacts = getString(R.string.contacts_colon) + " " + it.post.contactInfo
                binding.postDetail = it
                initApplicationBtn(it)
                binding.tvDeadlinePostDetail.text = deadline
                binding.tvContactsPostDetail.text = contacts
            }
            deletePostSuccessEvent.observe(owner) { finish() }
            sendApplicationSuccessEvent.observe(owner) {
                setApplicationBtn(R.string.cancel_apply) {
                    postDetailViewModel.sendApplication(postId)
                }
            }
            cancelApplicationSuccessEvent.observe(owner) {
                setApplicationBtn(R.string.do_apply) {
                    postDetailViewModel.cancelApplication(postId)
                }
            }
            postDetailBadRequestEvent.observe(owner) { makeToast(getString(R.string.bad_request)) }
            sendApplicationBadRequestEvent.observe(owner) { makeToast(getString(R.string.bad_request)) }
            retryEvent.observe(owner) { makeToast(getString(R.string.try_it_later)) }
            needToLoginEvent.observe(owner) { TODO("로그인 창 열기") }
            postNotFoundEvent.observe(owner) { makeToast(getString(R.string.post_not_found)) }
            conflictEvent.observe(owner) { makeToast(getString(R.string.conflict)) }
            applicationConflictEvent.observe(owner) { makeToast(getString(R.string.application_conflict)) }
            unknownErrorEvent.observe(owner) { makeToast(getString(R.string.unknown_error)) }
        }
    }

    private fun observeCommentViewModel() {
        val owner: LifecycleOwner = this
        commentViewModel.apply {
            commentListLiveData.observe(owner) { commentsAdapter.setCommentList(it) }
            addCommentSuccessEvent.observe(owner) { commentViewModel.getCommentList(postId) }
            fixCommentSuccessEvent.observe(owner) { commentViewModel.getCommentList(postId) }
            deleteCommentSuccessEvent.observe(owner) { commentViewModel.getCommentList(postId) }
            commentBadRequestEvent.observe(owner) { makeToast(getString(R.string.bad_request)) }
            retryEvent.observe(owner) { makeToast(getString(R.string.try_it_later)) }
            postNotFoundEvent.observe(owner) { makeToast(getString(R.string.post_not_found)) }
            commentNotFoundEvent.observe(owner) { makeToast(getString(R.string.comment_not_found)) }
            needToLoginEvent.observe(owner) { TODO("로그인 창 열기") }
            unknownErrorEvent.observe(owner) { makeToast(getString(R.string.unknown_error)) }
        }
    }

    private fun initApplicationBtn(postDetail: PostDetailEntity) {
        when {
            postDetail.isMine -> {
                setApplicationBtn(R.string.see_applications) {
                    val intent = Intent(this, PostApplicationsActivity::class.java)
                    intent.putExtra(KEY_POST_ID, postId)
                    intent.putExtra(KEY_START_DATE, postDetail.post.protectionStartDate)
                    intent.putExtra(KEY_END_DATE, postDetail.post.protectionEndDate)
                    startActivity(intent)
                }
            }
            postDetail.isApplied -> {
                setApplicationBtn(R.string.cancel_apply) {
                    postDetailViewModel.sendApplication(postId)
                }
            }
            else -> {
                setApplicationBtn(R.string.do_apply) {
                    postDetailViewModel.cancelApplication(postId)
                }
            }
        }
        binding.btnApplicationPostDetail.isEnabled = true
    }

    private fun setApplicationBtn(textId: Int, onClick: () -> Unit) {
        binding.btnApplicationPostDetail.text = getString(textId)
        binding.btnApplicationPostDetail.setOnClickListener { onClick() }
    }
}