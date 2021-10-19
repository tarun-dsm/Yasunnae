package com.semicolon.yasunnae.ui.writepost

import android.content.Intent
import android.view.View.INVISIBLE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.semicolon.domain.enum.AnimalType
import com.semicolon.domain.enum.toAnimalType
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.param.PostParam
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.adapter.PostImageListAdapter
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.base.IntentKeys
import com.semicolon.yasunnae.databinding.ActivityWritePostBinding
import com.semicolon.yasunnae.dialog.SelectDateDialog
import com.semicolon.yasunnae.util.toAnimalType
import com.semicolon.yasunnae.util.toDate
import java.io.File
import java.util.*

class WritePostActivity : BaseActivity<ActivityWritePostBinding>() {

    private var isEditMode: Boolean = false
    private var postId: Int = 0
    private var imageListAdapter = PostImageListAdapter(this)
    private var minDate: Date? = null
    private var maxDate: Date? = null

    override val layoutResId: Int
        get() = R.layout.activity_write_post

    private val writePostViewModel: WritePostViewModel by viewModels()

    override fun init() {
        binding.vpImageWritePost.adapter = imageListAdapter
        getIsEditMode()
        setUpView()
        binding.rgAnimalCategoriesWritePost.setOnCheckedChangeListener { _, _ ->
            isCompletable()
        }
        binding.etTitleWritePost.doOnTextChanged { _, _, _, count ->
            val textCount = "$count" + getText(R.string.limit_post_title)
            binding.tvCountTitleWritePost.text = textCount
            isCompletable()
        }
        binding.etDescriptionWritePost.doOnTextChanged { _, _, _, count ->
            val textCount = "$count" + getText(R.string.limit_post_description)
            binding.tvCountDescriptionWritePost.text = textCount
            isCompletable()
        }
        binding.etContactsWritePost.doOnTextChanged { _, _, _, count ->
            val textCount = "$count" + getText(R.string.limit_post_contacts)
            binding.tvCountContactsWritePost.text = textCount
            isCompletable()
        }
        binding.etPetNameWritePost.doOnTextChanged { _, _, _, _ ->
            isCompletable()
        }
        binding.etPetSpeciesWritePost.doOnTextChanged { _, _, _, _ ->
            isCompletable()
        }
        binding.etPetGenderWritePost.doOnTextChanged { _, _, _, _ ->
            isCompletable()
        }
        binding.ivStartDateWritePost.setOnClickListener {
            SelectDateDialog(
                context = this,
                maxDate = maxDate
            ) {
                minDate = it
                val date = "${it.year}-${it.month}-${it.date}"
                binding.tvStartDateWritePost.text = date
            }
            isCompletable()
        }
        binding.ivEndDateWritePost.setOnClickListener {
            SelectDateDialog(
                context = this,
                minDate = minDate
            ) {
                maxDate = it
                val date = "${it.year}-${it.month}-${it.date}"
                binding.tvEndDateWritePost.text = date
            }
            isCompletable()
        }
        binding.ivDeadlineWritePost.setOnClickListener {
            SelectDateDialog(
                context = this,
                maxDate = minDate
            ) {
                val date = "${it.year}-${it.month}-${it.date}"
                binding.tvDeadlineWritePost.text = date
            }
            isCompletable()
        }
        binding.btnDeleteImageWritePost.setOnClickListener {
            if (imageListAdapter.itemCount == 0) return@setOnClickListener
            imageListAdapter.deletePostImageList(binding.vpImageWritePost.currentItem)
            if (imageListAdapter.itemCount == 0) it.visibility = INVISIBLE
        }
        binding.btnAddImageWritePost.setOnClickListener {
            getImage()
        }
        binding.btnWritePost.setOnClickListener {
            writePost()
        }
    }

    override fun observe() {
        writePostViewModel.postIdLiveData.observe(this) {
            postId = it
            if (!isEditMode) writePostViewModel.sendImage(
                PostImageParam(it, imageListAdapter.getPostImageList())
            )
        }
        writePostViewModel.sendImageSuccessEvent.observe(this) {
            TODO("게시글 상세 페이지 열기")
        }
        writePostViewModel.badRequestEvent.observe(this) {
            makeToast(getString(R.string.bad_request))
        }
        writePostViewModel.retryEvent.observe(this) {
            makeToast(getString(R.string.try_it_later))
        }
        writePostViewModel.needToLoginEvent.observe(this) {
            finish()
            TODO("로그인 창 열기")
        }
        writePostViewModel.notFoundEvent.observe(this) {
            makeToast(getString(R.string.post_not_found))
            finish()
        }
        writePostViewModel.unknownEvent.observe(this) {
            makeToast(getString(R.string.unknown_error))
        }
    }

    private fun getIsEditMode() {
        isEditMode = intent.getBooleanExtra(IntentKeys.KEY_IS_EDIT_MODE, false)
        postId = intent.getIntExtra(IntentKeys.KEY_EDIT_POST_ID, 0)
    }

    private fun setUpView() {
        if (isEditMode) {
            binding.tvAppBarWritePost.text = getString(R.string.title_edit_post)
            checkCategory(intent.getStringExtra(IntentKeys.KEY_POST_CATEGORY)!!.toAnimalType())
            binding.etTitleWritePost.setText(intent.getStringExtra(IntentKeys.KEY_POST_TITLE))
            setStartDate(intent.getStringExtra(IntentKeys.KEY_START_DATE)!!)
            setEndDate(intent.getStringExtra(IntentKeys.KEY_END_DATE)!!)
            binding.tvCurDeadlineWritePost.text = intent.getStringExtra(IntentKeys.KEY_DEADLINE)
            binding.etDescriptionWritePost.setText(intent.getStringExtra(IntentKeys.KEY_DESCRIPTION))
            binding.etContactsWritePost.setText(intent.getStringExtra(IntentKeys.KEY_CONTACTS))
            imageListAdapter.setPostImageList(
                intent.getStringArrayListExtra(IntentKeys.KEY_POST_IMAGE_LIST)!!
            )
            binding.etPetNameWritePost.setText(intent.getStringExtra(IntentKeys.KEY_PET_NAME))
            binding.etPetSpeciesWritePost.setText(intent.getStringExtra(IntentKeys.KEY_PET_SPECIES))
            binding.etPetGenderWritePost.setText(intent.getStringExtra(IntentKeys.KEY_PET_SEX))
            binding.btnWritePost.text = getString(R.string.complete_edit)
        } else {
            binding.tvAppBarWritePost.text = getString(R.string.title_write_post)
            binding.btnWritePost.text = getString(R.string.complete_write)
        }
    }

    private fun isCompletable(): Boolean {
        if (binding.rgAnimalCategoriesWritePost.checkedRadioButtonId == -1) return false
        if (binding.etTitleWritePost.text.isEmpty()) return false
        if (binding.tvStartDateWritePost.text.isEmpty()) return false
        if (binding.tvEndDateWritePost.text.isEmpty()) return false
        if (binding.tvCurDeadlineWritePost.text.isEmpty()) return false
        if (binding.etDescriptionWritePost.text.isEmpty()) return false
        if (binding.etContactsWritePost.text.isEmpty()) return false
        if (imageListAdapter.itemCount == 0) return false
        if (binding.etPetNameWritePost.text.isEmpty()) return false
        if (binding.etPetSpeciesWritePost.text.isEmpty()) return false
        if (binding.etPetGenderWritePost.text.isEmpty()) return false
        binding.btnWritePost.isEnabled = true
        return true
    }

    private fun checkCategory(animalType: AnimalType) {
        when (animalType) {
            AnimalType.MAMMAL ->
                binding.rgAnimalCategoriesWritePost.check(R.id.rb_mammal_write_post)
            AnimalType.BIRD ->
                binding.rgAnimalCategoriesWritePost.check(R.id.rb_bird_write_post)
            AnimalType.REPTILES ->
                binding.rgAnimalCategoriesWritePost.check(R.id.rb_reptiles_write_post)
            AnimalType.AMPHIBIANS ->
                binding.rgAnimalCategoriesWritePost.check(R.id.rb_amphibians_write_post)
            AnimalType.FISH ->
                binding.rgAnimalCategoriesWritePost.check(R.id.rb_fish_write_post)
            AnimalType.ARTHROPODS ->
                binding.rgAnimalCategoriesWritePost.check(R.id.rb_arthropods_write_post)
            else -> return
        }
    }

    private fun setStartDate(date: String) {
        binding.tvStartDateWritePost.text = date
        minDate = date.toDate()
    }

    private fun setEndDate(date: String) {
        binding.tvEndDateWritePost.text = date
        maxDate = date.toDate()
    }

    private fun getImage() {
        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val intent = it.data
            if (intent != null) {
                if (intent.data != null) {
                    val path = intent.data!!.path
                    imageListAdapter.addPostImageList(File(path!!))
                }
            }
        }
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun writePost() {
        if (!isCompletable()) return
        val postParam = PostParam(
            title = binding.etTitleWritePost.text.toString(),
            protectionStartDate = binding.tvStartDateWritePost.text.toString(),
            protectionEndDate = binding.tvEndDateWritePost.text.toString(),
            applicationEndDate = binding.tvCurDeadlineWritePost.text.toString(),
            description = binding.etDescriptionWritePost.text.toString(),
            contactInfo = binding.etContactsWritePost.text.toString(),
            petName = binding.etPetNameWritePost.text.toString(),
            petSpecies = binding.etPetSpeciesWritePost.toString(),
            petSex = binding.etPetGenderWritePost.text.toString(),
            animalType = binding.rgAnimalCategoriesWritePost.checkedRadioButtonId.toAnimalType()
        )
        if (isEditMode) writePostViewModel.fixPost(FixedPostParam(postId, postParam))
        else writePostViewModel.writePost(postParam)
    }
}