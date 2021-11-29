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
import com.semicolon.yasunnae.base.IntentKeys.KEY_POST_ID
import com.semicolon.yasunnae.databinding.FragmentProfileBinding
import com.semicolon.yasunnae.dialog.AskDialog
import com.semicolon.yasunnae.dialog.ReportDialog
import com.semicolon.yasunnae.ui.coordinate.CoordinateActivity
import com.semicolon.yasunnae.ui.login.LoginActivity
import com.semicolon.yasunnae.ui.postdetail.PostDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val layoutResId: Int
        get() = R.layout.fragment_profile

    private val profileViewModel: ProfileViewModel by viewModels()
    private val reviewsAdapter = ReviewsAdapter(
        onEditClick = {
            // TODO("리뷰 수정 페이지로 이동")
        }, onDeleteClick = {
            AskDialog(requireContext(), getString(R.string.ask_delete_review), onYesClick = {
                profileViewModel.deleteReview(it.id)
            })
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
            }
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
                if (tab == reviewTab) {
                    binding.rvUserPost.visibility = INVISIBLE
                    binding.rvUserReview.visibility = VISIBLE
                } else if (tab == postTab) {
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
                if (it.experienceRaisingPet) binding.tvUserHasExperience.text =
                    getString(R.string.have)
                else binding.tvUserHasExperience.text = getString(R.string.none)
                if (it.locationConfirm) {
                    binding.btnSetLocation.text = getString(R.string.change)
                } else {
                    binding.btnSetLocation.text = getString(R.string.register)
                    binding.tvUserLocation.text = getString(R.string.no_location)
                    binding.tvUserLocation.setTextColor(resources.getColor(R.color.red_orange))
                }
            }
            reviewLiveData.observe(owner) {
                reviewsAdapter.setReviews(it)
            }
            profilePostLiveData.observe(owner) {
                profilePostsAdapter.setProfilePosts(it)
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
            getProfilePost(null)
        }
        binding.clMyProfile.visibility = VISIBLE
    }

    private fun getUserInfo(id: Int) {
        profileViewModel.run {
            getProfile(id)
            getReview(id)
            getProfilePost(id)
        }
        binding.clOthersProfile.visibility = INVISIBLE
    }

    private fun goToLogin() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    companion object IsMine {
        var IS_MINE: Boolean = true
        var USER_ID: Int = 0
    }
}