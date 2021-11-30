package com.semicolon.yasunnae.ui.postdetail

import android.content.Intent
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import com.semicolon.domain.entity.PostDetailEntity
import com.semicolon.domain.param.CommentParam
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.adapter.CommentsAdapter
import com.semicolon.yasunnae.adapter.PostDetailImageAdapter
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.base.IntentKeys
import com.semicolon.yasunnae.base.IntentKeys.KEY_CONTACTS
import com.semicolon.yasunnae.base.IntentKeys.KEY_DEADLINE
import com.semicolon.yasunnae.base.IntentKeys.KEY_DESCRIPTION
import com.semicolon.yasunnae.base.IntentKeys.KEY_EDIT_POST_ID
import com.semicolon.yasunnae.base.IntentKeys.KEY_END_DATE
import com.semicolon.yasunnae.base.IntentKeys.KEY_IS_EDIT_MODE
import com.semicolon.yasunnae.base.IntentKeys.KEY_PET_NAME
import com.semicolon.yasunnae.base.IntentKeys.KEY_PET_SEX
import com.semicolon.yasunnae.base.IntentKeys.KEY_PET_SPECIES
import com.semicolon.yasunnae.base.IntentKeys.KEY_POST_CATEGORY
import com.semicolon.yasunnae.base.IntentKeys.KEY_POST_ID
import com.semicolon.yasunnae.base.IntentKeys.KEY_POST_IMAGE_LIST
import com.semicolon.yasunnae.base.IntentKeys.KEY_POST_TITLE
import com.semicolon.yasunnae.base.IntentKeys.KEY_START_DATE
import com.semicolon.yasunnae.databinding.ActivityPostDetailBinding
import com.semicolon.yasunnae.dialog.AskDialog
import com.semicolon.yasunnae.dialog.EditCommentDialog
import com.semicolon.yasunnae.ui.login.LoginActivity
import com.semicolon.yasunnae.ui.postapplications.PostApplicationsActivity
import com.semicolon.yasunnae.ui.profile.ProfileActivity
import com.semicolon.yasunnae.ui.writepost.WritePostActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_post_detail

    private val postDetailViewModel: PostDetailViewModel by viewModels()
    private val commentViewModel: CommentViewModel by viewModels()
    private var postId = 0
    private var postDetail: PostDetailEntity? = null

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

    override fun onResume() {
        super.onResume()
        init()
    }

    override fun init() {
        postId = intent.getIntExtra(KEY_POST_ID, 0)
        binding.vpImagePostDetail.adapter = postDetailImageAdapter
        binding.rvCommentsPostDetail.adapter = commentsAdapter
        binding.btnBack.setOnClickListener { finish() }
        binding.tvWriterName.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(IntentKeys.KEY_USER_ID, postDetail!!.writerId)
            startActivity(intent)
        }
        binding.btnEditPost.setOnClickListener {
            goToEditPost()
        }
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
                if (it.isMine) postDetailViewModel.getProfile(null)
                else postDetailViewModel.getProfile(it.writerId)
                postDetail = it
                val deadline = getString(R.string.deadline_colon) + " " + it.post.applicationEndDate
                val contacts = getString(R.string.contacts_colon) + " " + it.post.contactInfo
                binding.postDetail = it
                initApplicationBtn(it)
                binding.tvDeadlinePostDetail.text = deadline
                binding.tvContactsPostDetail.text = contacts
                binding.tvPetGenderPostDetail.text =
                    if (it.pet.petSex == "MALE") getString(R.string.male) else getString(R.string.female)
                postDetailImageAdapter.setImageList(it.pet.filePaths)
                binding.indicatorImagePostDetail.setViewPager2(binding.vpImagePostDetail)
                if (it.post.isUpdated) {
                    binding.ivIsUpdatedPostDetail.visibility = VISIBLE
                    binding.tvIsUpdatedPostDetail.visibility = VISIBLE
                }
            }
            deletePostSuccessEvent.observe(owner) {
                makeToast(getString(R.string.complete_delete_post))
                finish()
            }
            profileLiveData.observe(owner) {
                binding.profile = it
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
            needToLoginEvent.observe(owner) { openLoginActivity() }
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
            needToLoginEvent.observe(owner) { openLoginActivity() }
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
                binding.btnEditPost.visibility = VISIBLE
                binding.btnDeletePost.visibility = VISIBLE
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

    private fun goToEditPost() {
        if (postDetail == null) return
        val intent = Intent(this, WritePostActivity::class.java)
        intent.putExtra(KEY_IS_EDIT_MODE, true)
        intent.putExtra(KEY_EDIT_POST_ID, postId)
        intent.putExtra(KEY_POST_CATEGORY, postDetail!!.pet.animalType.toString())
        intent.putExtra(KEY_POST_TITLE, postDetail!!.post.title)
        intent.putExtra(KEY_START_DATE, postDetail!!.post.protectionStartDate)
        intent.putExtra(KEY_END_DATE, postDetail!!.post.protectionEndDate)
        intent.putExtra(KEY_DEADLINE, postDetail!!.post.applicationEndDate)
        intent.putExtra(KEY_DESCRIPTION, postDetail!!.post.description)
        intent.putExtra(KEY_CONTACTS, postDetail!!.post.contactInfo)
        intent.putExtra(KEY_POST_IMAGE_LIST, ArrayList(postDetail!!.pet.filePaths))
        intent.putExtra(KEY_PET_NAME, postDetail!!.pet.petName)
        intent.putExtra(KEY_PET_SPECIES, postDetail!!.pet.petSpecies)
        intent.putExtra(KEY_PET_SEX, postDetail!!.pet.petSex)
        startActivity(intent)
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}