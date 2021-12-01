package com.semicolon.yasunnae.ui.profile

import android.content.Intent
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.semicolon.domain.param.ReportParam
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.adapter.ProfilePostsAdapter
import com.semicolon.yasunnae.adapter.ReviewsAdapter
import com.semicolon.yasunnae.base.BaseFragment
import com.semicolon.yasunnae.base.IntentKeys.KEY_COMMENT_POST
import com.semicolon.yasunnae.base.IntentKeys.KEY_IS_EDIT_MODE
import com.semicolon.yasunnae.base.IntentKeys.KEY_POST_ID
import com.semicolon.yasunnae.base.IntentKeys.KEY_USER_ID
import com.semicolon.yasunnae.databinding.FragmentProfileBinding
import com.semicolon.yasunnae.dialog.AskDialog
import com.semicolon.yasunnae.dialog.ReportDialog
import com.semicolon.yasunnae.ui.coordinate.CoordinateActivity
import com.semicolon.yasunnae.ui.login.LoginActivity
import com.semicolon.yasunnae.ui.postdetail.PostDetailActivity
import com.semicolon.yasunnae.ui.review.WriteReviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val layoutResId: Int
        get() = R.layout.fragment_profile

    private val profileViewModel: ProfileViewModel by viewModels()
    private val reviewsAdapter = ReviewsAdapter(
        onEditClick = {
            val intent = Intent(context, WriteReviewActivity::class.java)
            intent.putExtra(KEY_IS_EDIT_MODE, true)
            intent.putExtra(KEY_USER_ID, USER_ID)
            intent.putExtra(KEY_COMMENT_POST, it.comment)
            startActivity(intent)
        }, onDeleteClick = {
            AskDialog(requireContext(), getString(R.string.ask_delete_review), onYesClick = {
                profileViewModel.deleteReview(it.id)
            }).callDialog()
        })
    private val profilePostsAdapter = ProfilePostsAdapter {
        val intent = Intent(context, PostDetailActivity::class.java)
        intent.putExtra(KEY_POST_ID, it.id)
        startActivity(intent)
    }

    override fun init() {
        val reviewTab = binding.tlUserHistory.newTab().setText(R.string.review)
        val postTab = binding.tlUserHistory.newTab().setText(R.string.post)
        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()
        }
        binding.btnReport.setOnClickListener {
            ReportDialog(requireContext()) {
                profileViewModel.reportUser(ReportParam(USER_ID, it))
            }.callDialog()
        }
        binding.btnSetLocation.setOnClickListener {
            val intent = Intent(context, CoordinateActivity::class.java)
            startActivity(intent)
        }
        binding.tlUserHistory
            .addTab(reviewTab)
        binding.tlUserHistory
            .addTab(postTab)
        binding.tlUserHistory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setNoContent(false)
                if (tab == reviewTab) {
                    profileViewModel.getReview(if (IS_MINE) null else USER_ID)
                    binding.rvUserPost.visibility = INVISIBLE
                    binding.rvUserReview.visibility = VISIBLE
                } else if (tab == postTab) {
                    profileViewModel.getProfilePost(if (IS_MINE) null else USER_ID)
                    binding.rvUserReview.visibility = INVISIBLE
                    binding.rvUserPost.visibility = VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        binding.rvUserReview.adapter = reviewsAdapter
        binding.rvUserPost.adapter = profilePostsAdapter

        if (IS_MINE) getMyInfo()
        else getUserInfo(USER_ID)
    }

    override fun observe() {
        val owner = this
        profileViewModel.apply {
            profileLiveData.observe(owner) {
                val myName = it.nickname + getString(R.string.respect)
                binding.profile = it
                binding.tvMyName.text = myName
                if (it.experienceRaisingPet)
                    binding.tvUserHasExperience.text = getString(R.string.have)
                else binding.tvUserHasExperience.text = getString(R.string.none)
                if (it.locationConfirm) {
                    binding.btnSetLocation.text = getString(R.string.change)
                    binding.tvUserLocation.text = it.administrationDivision
                } else {
                    binding.btnSetLocation.text = getString(R.string.register)
                    binding.tvUserLocation.text = getString(R.string.no_location)
                    binding.tvUserLocation.setTextColor(resources.getColor(R.color.red_orange))
                }
                if (IS_MINE) binding.btnSetLocation.visibility = VISIBLE
            }
            reviewLiveData.observe(owner) {
                reviewsAdapter.setReviews(it)
                setNoContent(it.isEmpty(), getString(R.string.no_review))
            }
            profilePostLiveData.observe(owner) {
                profilePostsAdapter.setProfilePosts(it)
                setNoContent(it.isEmpty(), getString(R.string.no_post))
            }
            reportSuccessEvent.observe(owner) {
                makeToast(getString(R.string.complete_report))
            }
            deleteReviewSuccessEvent.observe(owner) {
                if (IS_MINE) getReview(null)
                else getReview(USER_ID)
            }
            logoutSuccessEvent.observe(owner) {
                goToLogin()
            }
            retryEvent.observe(owner) {
                makeToast(getString(R.string.try_it_later))
            }
            needToLoginEvent.observe(owner) {
                goToLogin()
            }
            unknownErrorEvent.observe(owner) {
                makeToast(getString(R.string.unknown_error))
            }
        }
    }

    private fun getMyInfo() {
        profileViewModel.run {
            getProfile(null)
            getReview(null)
        }
        binding.clMyProfile.visibility = VISIBLE
    }

    private fun getUserInfo(id: Int) {
        profileViewModel.run {
            getProfile(id)
            getReview(id)
        }
        binding.clOthersProfile.visibility = VISIBLE
    }

    private fun goToLogin() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun setNoContent(isNoContent: Boolean, message: String = "") {
        if (isNoContent) {
            binding.tvNoContent.text = message
            binding.ivNoContent.visibility = VISIBLE
            binding.tvNoContent.visibility = VISIBLE
        } else {
            binding.ivNoContent.visibility = INVISIBLE
            binding.tvNoContent.visibility = INVISIBLE
        }
    }

    companion object IsMine {
        var IS_MINE: Boolean = true
        var USER_ID: Int = 0
    }
}