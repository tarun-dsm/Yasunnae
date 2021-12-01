package com.semicolon.yasunnae.ui.applications

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.adapter.MyApplicationAdapter
import com.semicolon.yasunnae.base.BaseFragment
import com.semicolon.yasunnae.base.IntentKeys
import com.semicolon.yasunnae.databinding.FragmentMyapplicationsListBinding
import com.semicolon.yasunnae.ui.coordinate.CoordinateActivity
import com.semicolon.yasunnae.ui.login.LoginActivity
import com.semicolon.yasunnae.ui.postdetail.PostDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyApplicationsListFragment : BaseFragment<FragmentMyapplicationsListBinding>() {

    override val layoutResId: Int
        get() = R.layout.fragment_myapplications_list

    private val myapplicationsViewModel: MyApplicationsViewModel by viewModels()
    private val applicationAdapter = MyApplicationAdapter(object : MyApplicationAdapter.OnItemClickListener {
        override fun onItemClick(postId: Int) {
            val intent = Intent(context, PostDetailActivity::class.java)
            intent.putExtra(IntentKeys.KEY_POST_ID, postId)
            startActivity(intent)
        }
    })

    override fun init() {
        binding.appliviewModel = myapplicationsViewModel
        binding.rvPostList.adapter = applicationAdapter
        myapplicationsViewModel.getPostList()
        myapplicationsViewModel.isLocationVerified()
        myapplicationsViewModel.getPostList()
    }


    override fun observe() {
        myapplicationsViewModel.postListLiveDate.observe(this) {
            applicationAdapter.setList(it)
        }
        myapplicationsViewModel.retryEvent.observe(this) {
            makeToast(getString(R.string.try_it_later))
        }
        myapplicationsViewModel.needToLoginEvent.observe(this) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        myapplicationsViewModel.unknownErrorEvent.observe(this) {
            makeToast(getString(R.string.unknown_error))
        }
    }


    private fun isListEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            binding.rvPostList.visibility = View.INVISIBLE
            binding.clEmptyPostList.visibility = View.VISIBLE
        } else {
            binding.rvPostList.visibility = View.VISIBLE
            binding.clEmptyPostList.visibility = View.INVISIBLE
        }
    }

    private fun startVerifyLocationActivity() {
        val intent = Intent(context, CoordinateActivity::class.java)
        startActivity(intent)
    }
}