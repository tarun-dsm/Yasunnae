package com.semicolon.yasunnae.ui.postdetail

import android.content.Intent
import android.view.View
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
import com.semicolon.yasunnae.dialog.AskDialog
import com.semicolon.yasunnae.dialog.EditCommentDialog
import com.semicolon.yasunnae.ui.postapplications.PostApplicationsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_post_detail

    private val postDetailViewModel: PostDetailViewModel by viewModels()
    private val commentViewModel: CommentViewModel by viewModels()
    private var postId = 0

    private val postDetailImageAdapter = PostDetailImageAdapter()
    private val commentsAdapter = CommentsAdapter(
        onEditClick = {
            EditCommentDialog(this, it.comment) { fixedComment ->
                commentViewModel.fixComment(CommentParam(it.id, fixedComment))
            }.callDialog()
        },
        onDeleteClick = {
            AskDialog(this, getString(R.string.ask_delete_comment), onYesClick = {
                commentViewModel.deleteComment(it.id)
            }).callDialog()
        }
    )

    override fun init() {
        postId = intent.getIntExtra(KEY_POST_ID, 0)
        binding.vpImagePostDetail.adapter = postDetailImageAdapter
        binding.rvCommentsPostDetail.adapter = commentsAdapter
        binding.btnBack.setOnClickListener { finish() }
        binding.btnDeletePost.setOnClickListener {
            AskDialog(this, getString(R.string.ask_delete_post), onYesClick = {
                postDetailViewModel.deletePost(postId)
            }).callDialog()
        }
        binding.btnSendComment.setOnClickListener {
            val comment = binding.etCommentPostDetail.text.toString()
            if (comment.isNotEmpty()) commentViewModel.addComment(CommentParam(postId, comment))
            binding.etCommentPostDetail.text.clear()
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
            deletePostSuccessEvent.observe(owner) {
                makeToast(getString(R.string.complete_delete_post))
                finish()
            }
            sendApplicationSuccessEvent.observe(owner) {
                setApplicationBtn(R.string.cancel_apply) {
                    postDetailViewModel.cancelApplication(postId)
                }
                binding.btnApplicationPostDetail.isEnabled = true
            }
            cancelApplicationSuccessEvent.observe(owner) {
                setApplicationBtn(R.string.do_apply) {
                    postDetailViewModel.sendApplication(postId)
                }
                binding.btnApplicationPostDetail.isEnabled = true
            }
            postDetailBadRequestEvent.observe(owner) { makeToast(getString(R.string.bad_request)) }
            sendApplicationBadRequestEvent.observe(owner) {
                makeToast(getString(R.string.bad_request))
                binding.btnApplicationPostDetail.isEnabled = true
            }
            retryEvent.observe(owner) { makeToast(getString(R.string.try_it_later)) }
            needToLoginEvent.observe(owner) { TODO("로그인 창 열기") }
            postNotFoundEvent.observe(owner) { makeToast(getString(R.string.post_not_found)) }
            conflictEvent.observe(owner) { makeToast(getString(R.string.conflict)) }
            applicationConflictEvent.observe(owner) {
                makeToast(getString(R.string.application_conflict))
                binding.btnApplicationPostDetail.isEnabled = true
            }
            unknownErrorEvent.observe(owner) { makeToast(getString(R.string.unknown_error)) }
        }
    }

    private fun observeCommentViewModel() {
        val owner: LifecycleOwner = this
        commentViewModel.apply {
            commentListLiveData.observe(owner) {
                commentsAdapter.setCommentList(it)
            }
            addCommentSuccessEvent.observe(owner) { commentViewModel.getCommentList(postId) }
            fixCommentSuccessEvent.observe(owner) { commentViewModel.getCommentList(postId) }
            deleteCommentSuccessEvent.observe(owner) {
                makeToast(getString(R.string.complete_delete_comment))
                commentViewModel.getCommentList(postId)
            }
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
                binding.btnApplicationPostDetail.isEnabled = true
                binding.btnEditPost.visibility = View.VISIBLE
                binding.btnDeletePost.visibility = View.VISIBLE
            }
            postDetail.post.isApplicationEnd -> {
                setApplicationBtn(R.string.application_end) {}
            }
            postDetail.isApplied -> {
                setApplicationBtn(R.string.cancel_apply) {
                    postDetailViewModel.cancelApplication(postId)
                    binding.btnApplicationPostDetail.isEnabled = false
                }
                binding.btnApplicationPostDetail.isEnabled = true
            }
            else -> {
                setApplicationBtn(R.string.do_apply) {
                    postDetailViewModel.sendApplication(postId)
                    binding.btnApplicationPostDetail.isEnabled = false
                }
                binding.btnApplicationPostDetail.isEnabled = true
            }
        }
    }

    private fun setApplicationBtn(textId: Int, onClick: () -> Unit) {
        binding.btnApplicationPostDetail.text = getString(textId)
        binding.btnApplicationPostDetail.setOnClickListener { onClick() }
    }
}