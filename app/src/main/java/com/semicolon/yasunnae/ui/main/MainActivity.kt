package com.semicolon.yasunnae.ui.main

import androidx.fragment.app.Fragment
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.databinding.ActivityMainBinding
import com.semicolon.yasunnae.ui.postlist.PostListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun init() {
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> setFragment(PostListFragment())
                R.id.menu_write_post -> startWritePostActivity()
                R.id.menu_my_applications -> TODO("내 신청목록 Fragment 실행")
                R.id.menu_my_profile -> TODO("마이 프로필 Fragment 로 실행")
            }
            return@setOnItemSelectedListener true
        }
        binding.bnvMain.selectedItemId = R.id.menu_home
    }

    override fun observe() {}

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            binding.flContainerMail.id, fragment
        ).commit()
    }

    private fun startWritePostActivity() {
        TODO("게시글 작성 Activity 실행")
    }

}