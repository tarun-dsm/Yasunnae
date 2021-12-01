package com.semicolon.yasunnae.ui.postlist

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.view.View
import androidx.fragment.app.viewModels
import com.semicolon.domain.enum.AnimalType
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.adapter.PostListAdapter
import com.semicolon.yasunnae.base.BaseFragment
import com.semicolon.yasunnae.base.IntentKeys.KEY_POST_ID
import com.semicolon.yasunnae.databinding.FragmentPostListBinding
import com.semicolon.yasunnae.ui.coordinate.CoordinateActivity
import com.semicolon.yasunnae.dialog.NeedToVerifyLocationDialog
import com.semicolon.yasunnae.ui.login.LoginActivity
import com.semicolon.yasunnae.ui.postdetail.PostDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostListFragment : BaseFragment<FragmentPostListBinding>() {

    override val layoutResId: Int
        get() = R.layout.fragment_post_list

    private val postListViewModel: PostListViewModel by viewModels()
    private val postListAdapter = PostListAdapter(object : PostListAdapter.OnItemClickListener {
        override fun onItemClick(postId: Int) {
            val intent = Intent(context, PostDetailActivity::class.java)
            intent.putExtra(KEY_POST_ID, postId)
            startActivity(intent)
        }
    })

    override fun init() {
        binding.viewModel = postListViewModel
        binding.rvPostList.adapter = postListAdapter
        binding.btnGoVerifyLocation.setOnClickListener {
            startVerifyLocationActivity()
        }
        binding.rgAnimalCategories.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_all -> allCategory()
                R.id.rb_mammal -> setCategory(AnimalType.MAMMEL)
                R.id.rb_bird -> setCategory(AnimalType.BIRD)
                R.id.rb_reptiles -> setCategory(AnimalType.REPTILES)
                R.id.rb_amphibians -> setCategory(AnimalType.AMPHIBIANS)
                R.id.rb_fish -> setCategory(AnimalType.FISH)
                R.id.rb_arthropods -> setCategory(AnimalType.ARTHROPODS)
                else -> setCategory(AnimalType.WRONG_TYPE)
            }
        }
        binding.rgAnimalCategories.check(R.id.rb_all)
        postListViewModel.isLocationVerified()
        postListViewModel.getPostList()
    }

    override fun observe() {
        postListViewModel.isLocationVerifiedLiveData.observe(this) {
            if (it) {
                binding.clBannerLocationVerified.visibility = View.VISIBLE
            } else {
                binding.clBannerLocationNotVerified.visibility = View.VISIBLE
                NeedToVerifyLocationDialog(requireContext()) {
                    startVerifyLocationActivity()
                }.callDialog()
            }
        }
        postListViewModel.postListLiveDate.observe(this) {
            postListAdapter.setList(it)
            allCategory()
        }
        postListViewModel.retryEvent.observe(this) {
            makeToast(getString(R.string.try_it_later))
        }
        postListViewModel.needToLoginEvent.observe(this) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        postListViewModel.unknownErrorEvent.observe(this) {
            makeToast(getString(R.string.unknown_error))
        }
    }

    private fun allCategory() {
        isListEmpty(!postListAdapter.resetCategory())
    }

    private fun setCategory(category: AnimalType) {
        isListEmpty(!postListAdapter.setCategory(category))
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