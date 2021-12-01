package com.semicolon.yasunnae.ui.main

import android.content.Intent
import androidx.fragment.app.Fragment
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.databinding.ActivityMainBinding
import com.semicolon.yasunnae.ui.applications.MyApplicationsListFragment
import com.semicolon.yasunnae.ui.postlist.PostListFragment
import com.semicolon.yasunnae.ui.profile.ProfileFragment
import com.semicolon.yasunnae.ui.writepost.WritePostActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun onResume() {
        super.onResume()
        init()
    }

    override fun init() {
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> setFragment(PostListFragment())
                R.id.menu_write_post -> startWritePostActivity()
                R.id.menu_my_applications -> setFragment(MyApplicationsListFragment())
                R.id.menu_my_profile -> {
                    val profileFragment = ProfileFragment()
                    setFragment(profileFragment)
                    ProfileFragment.IS_MINE = true
                }
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
        val intent = Intent(this, WritePostActivity::class.java)
        startActivity(intent)
    }

}