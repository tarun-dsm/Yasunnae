package com.semicolon.yasunnae.ui.postlist

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.semicolon.domain.enum.AnimalType
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.adapter.PostListAdapter
import com.semicolon.yasunnae.base.BaseFragment
import com.semicolon.yasunnae.databinding.FragmentPostListBinding
import com.semicolon.yasunnae.ui.coordinate.CoordinateActivity
import com.semicolon.yasunnae.ui.dialog.NeedToVerifyLocationDialog

class PostListFragment : BaseFragment<FragmentPostListBinding>() {

    override val layoutResId: Int
        get() = R.layout.fragment_post_list

    private val postListViewModel: PostListViewModel by viewModels()
    private val postListAdapter = PostListAdapter(object : PostListAdapter.OnItemClickListener {
        override fun onItemClick(postId: Int) {
            TODO("게시물 상세 페이지로 이동")
        }
    })

    override fun init() {
        binding.rvPostList.adapter = postListAdapter
        binding.btnGoVerifyLocation.setOnClickListener {
            startVerifyLocationActivity()
        }
        binding.rgAnimalCategories.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == group.checkedRadioButtonId) {
                group.clearCheck()
                postListAdapter.resetCategory()
            } else when (checkedId) {
                R.id.rb_mammal -> setCategory(AnimalType.MAMMAL)
                R.id.rb_bird -> setCategory(AnimalType.BIRD)
                R.id.rb_reptiles -> setCategory(AnimalType.REPTILES)
                R.id.rb_amphibians -> setCategory(AnimalType.AMPHIBIANS)
                R.id.rb_fish -> setCategory(AnimalType.FISH)
                R.id.rb_arthropods -> setCategory(AnimalType.ARTHROPODS)
                else -> setCategory(AnimalType.WRONG_TYPE)
            }
        }
        postListViewModel.isLocationVerified()
        postListViewModel.getPostList()
    }

    override fun observe() {
        postListViewModel.isLocationVerifiedLiveData.observe(this) {
            NeedToVerifyLocationDialog(requireContext()) {
                startVerifyLocationActivity()
            }.callDialog()
        }
        postListViewModel.postListLiveDate.observe(this) {
            postListAdapter.setList(it)
        }
        postListViewModel.retryEvent.observe(this) {
            makeToast(getString(R.string.try_it_later))
        }
        postListViewModel.needToLoginEvent.observe(this) {
            TODO("로그인 창으로 이동")
        }
        postListViewModel.unknownErrorEvent.observe(this) {
            makeToast(getString(R.string.unknown_error))
        }
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