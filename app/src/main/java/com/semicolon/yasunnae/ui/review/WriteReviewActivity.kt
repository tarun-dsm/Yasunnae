package com.semicolon.yasunnae.ui.review

import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.base.IntentKeys
import dagger.hilt.android.AndroidEntryPoint
import com.semicolon.domain.param.ReviewParam
import com.semicolon.yasunnae.base.IntentKeys.KEY_USER_ID
import com.semicolon.yasunnae.databinding.ActivityWriteReviewBinding

@AndroidEntryPoint
class WriteReviewActivity : BaseActivity<ActivityWriteReviewBinding>() {
    private var isEditMode: Boolean = false
    private var postId: Int = 0
    private var userId = 0
    private var curRating: Float = 0F

    override val layoutResId: Int
        get() = R.layout.activity_write_review

    private val writeReviewViewModel: WritReviewViewModel by viewModels()


    override fun init() {
        postId = intent.getIntExtra(IntentKeys.KEY_POST_ID, 0)
        userId = intent.getIntExtra(KEY_USER_ID, 0)
        getIsEditMode()
        setUpView()
        binding.btnBackWritePost.setOnClickListener { finish() }
        binding.btnWritePost.setOnClickListener {
            writePost()
        }

        binding.etComentWritePost.doOnTextChanged { _, _, _, _ ->
            isCompletable()
        }
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            curRating = rating
        }
    }

    private fun getIsEditMode() {
        isEditMode = intent.getBooleanExtra(IntentKeys.KEY_IS_EDIT_MODE, false)
        postId = intent.getIntExtra(IntentKeys.KEY_EDIT_POST_ID, 0)
    }

    private fun setUpView() {
        if (isEditMode) {
            binding.etComentWritePost.setText(intent.getStringExtra(IntentKeys.KEY_COMMENT_POST))
            binding.btnWritePost.text = getString(R.string.complete_edit)
        } else {
            binding.tvAppBarWritePost.text = getString(R.string.review_write)
            binding.btnWritePost.text = getString(R.string.review_write)
        }
    }

    private fun isCompletable(): Boolean {
        if (binding.etComentWritePost.text.isEmpty()) return false
        binding.btnWritePost.isEnabled = true
        return true
    }


    private fun writePost() {
        if (!isCompletable()) return
        val reviewParam = ReviewParam(
            id = userId,
            grade = curRating.toDouble(),
            comment = binding . etComentWritePost . text . toString ()
        )
        if (isEditMode) writeReviewViewModel.fixReview(reviewParam)
        else writeReviewViewModel.writeReview(reviewParam)
        binding.btnWritePost.isEnabled = false
    }

    override fun observe() {
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