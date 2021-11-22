package com.semicolon.yasunnae.ui.review

import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.loader.content.CursorLoader
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.param.PostParam
import com.semicolon.yasunnae.R
import java.util.*
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.base.IntentKeys
import com.semicolon.yasunnae.ui.review.WriteReviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.widget.TextView

@AndroidEntryPoint
class WriteReviewActivity : BaseActivity<ActivityWritReviewBinding>() {
    private var isEditMode: Boolean = false
    private var postId: Int = 0

    override val layoutResId: Int
        get() = R.layout.activity_write_review

    private val writeReviewViewModel: WriteReviewViewModel by viewModels()

    override fun init() {
        val commentCount = "0${getText(R.string.Limit_post_comment)}"

        binding.tvCountCommentWritePost.text = commentCount

        getIsEditMode()
        setUpView()
        binding.btnBackWritePost.setOnClickListener { finish() }

        binding.etReviewWritePost.doOnTextChanged { text, _, _, _ ->
            val textCount = "${text?.length}" + getText(R.string.Limit_post_comment)
            binding.tvCountCommentWritePost.text = textCount
            isCompletable()
        }

        binding.btnWritePost.setOnClickListener {
            writePost()
        }
    }

    private fun getIsEditMode() {
        isEditMode = intent.getBooleanExtra(IntentKeys.KEY_IS_EDIT_MODE, false)
        postId = intent.getIntExtra(IntentKeys.KEY_EDIT_POST_ID, 0)
    }

    private fun setUpView() {
        if (isEditMode) {
            binding.etCommentWritePost.setText(intent.getStringExtra(IntentKeys.KEY_COMMENT_POST))
            binding.btnWritePost.text = getString(R.string.complete_edit)
        } else {
            binding.tvAppBarWritePost.text = getString(R.string.review_write)
            binding.btnWritePost.text = getString(R.string.review_write)
        }
    }

    private fun isCompletable(): Boolean {
        if (binding.etCommentWritePost.text.isEmpty()) return false
        binding.btnWritePost.isEnabled = true
        return true
    }


    private fun writePost() {
        if (!isCompletable()) return
        val postParam = PostParam(
            comment = binding.etCommentWritePost.text.toString()
        )
        if (isEditMode) writeReviewViewModel.fixPost(FixedPostParam(postId, postParam))
        else writeReviewViewModel.writePost(postParam)
        binding.btnWriteComment.isEnabled = false
    }

    override fun observe() {
        writeReviewViewModel.postIdLiveData.observe(this) {
            postId = it
        }
        writeReviewViewModel.badRequestEvent.observe(this) {
            makeToast(getString(R.string.bad_request))
        }
        writeReviewViewModel.retryEvent.observe(this) {
            makeToast(getString(R.string.try_it_later))
        }
        writeReviewViewModel.needToLoginEvent.observe(this) {
            finish()
            TODO("로그인 창 열기")
        }
        writeReviewViewModel.notFoundEvent.observe(this) {
            makeToast(getString(R.string.post_not_found))
            finish()
        }
        writeReviewViewModel.unknownEvent.observe(this) {
            makeToast(getString(R.string.unknown_error))
        }
    }
}