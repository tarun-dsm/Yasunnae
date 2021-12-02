package com.semicolon.yasunnae.ui.applications

import android.content.Intent
import androidx.fragment.app.viewModels
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.adapter.MyApplicationAdapter
import com.semicolon.yasunnae.base.BaseFragment
import com.semicolon.yasunnae.base.IntentKeys
import com.semicolon.yasunnae.databinding.FragmentMyapplicationsListBinding
import com.semicolon.yasunnae.ui.login.LoginActivity
import com.semicolon.yasunnae.ui.postdetail.PostDetailActivity
import com.semicolon.yasunnae.ui.review.WriteReviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyApplicationsListFragment : BaseFragment<FragmentMyapplicationsListBinding>() {

    override val layoutResId: Int
        get() = R.layout.fragment_myapplications_list

    private val myApplicationsViewModel: MyApplicationsViewModel by viewModels()

    private val applicationAdapter = MyApplicationAdapter(
        onItemClick = {
            val intent = Intent(context, PostDetailActivity::class.java)
            intent.putExtra(IntentKeys.KEY_POST_ID, it)
            startActivity(intent)

        },
        onWriteReviewClick = {
            val intent = Intent(context, WriteReviewActivity::class.java)
            intent.putExtra(IntentKeys.KEY_APPLICATION_ID, it)
            startActivity(intent)
        })

    override fun init() {
        binding.appliviewModel = myApplicationsViewModel
        binding.rvPostList.adapter = applicationAdapter
        myApplicationsViewModel.getPostList()
        myApplicationsViewModel.isLocationVerified()
        myApplicationsViewModel.getPostList()
    }


    override fun observe() {
        myApplicationsViewModel.postListLiveDate.observe(this) {
            applicationAdapter.setList(it)
        }
        myApplicationsViewModel.retryEvent.observe(this) {
            makeToast(getString(R.string.try_it_later))
        }
        myApplicationsViewModel.needToLoginEvent.observe(this) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        myApplicationsViewModel.unknownErrorEvent.observe(this) {
            makeToast(getString(R.string.unknown_error))
        }
    }
}