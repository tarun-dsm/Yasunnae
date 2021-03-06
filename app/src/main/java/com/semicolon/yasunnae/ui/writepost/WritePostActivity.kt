package com.semicolon.yasunnae.ui.writepost

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.MediaStore
import android.view.View.INVISIBLE
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.semicolon.domain.enum.AnimalType
import com.semicolon.domain.enum.Sex
import com.semicolon.domain.enum.toAnimalType
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.param.PostParam
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.adapter.PostImageListAdapter
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.base.IntentKeys
import com.semicolon.yasunnae.base.IntentKeys.KEY_POST_ID
import com.semicolon.yasunnae.databinding.ActivityWritePostBinding
import com.semicolon.yasunnae.dialog.SelectDateDialog
import com.semicolon.yasunnae.ui.postdetail.PostDetailActivity
import com.semicolon.yasunnae.util.toAnimalType
import com.semicolon.yasunnae.util.toDate
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedRxImagePicker
import java.io.File
import java.util.*
import android.database.Cursor

import android.net.Uri
import android.view.View.VISIBLE
import androidx.loader.content.CursorLoader

import com.semicolon.domain.*
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.yasunnae.ui.login.LoginActivity

@AndroidEntryPoint
class WritePostActivity : BaseActivity<ActivityWritePostBinding>() {

    private var isEditMode: Boolean = false
    private var postId: Int = 0
    private var minDate: Date? = null
    private var maxDate: Date? = null
    private var deadline: Date? = null
    private val imageListAdapter = PostImageListAdapter(this) {
        binding.indicatorImageWritePost.refreshDots()
        binding.cvEmptyImage.visibility = INVISIBLE
        binding.vpImageWritePost.visibility = VISIBLE
        binding.btnDeleteImageWritePost.visibility = VISIBLE
    }

    override val layoutResId: Int
        get() = R.layout.activity_write_post

    private val writePostViewModel: WritePostViewModel by viewModels()

    override fun init() {
        val titleCount = "0${getText(R.string.limit_post_title)}"
        val descriptionCount = "0${getText(R.string.limit_post_description)}"
        val contactsCount = "0${getText(R.string.limit_post_contacts)}"

        binding.tvCountTitleWritePost.text = titleCount
        binding.tvCountDescriptionWritePost.text = descriptionCount
        binding.tvCountContactsWritePost.text = contactsCount
        binding.vpImageWritePost.adapter = imageListAdapter
        binding.indicatorImageWritePost.setViewPager2(binding.vpImageWritePost)
        getIsEditMode()
        setUpView()
        binding.btnBackWritePost.setOnClickListener { finish() }
        binding.rgAnimalCategoriesWritePost.setOnCheckedChangeListener { _, _ ->
            isCompletable()
        }
        binding.etTitleWritePost.doOnTextChanged { text, _, _, _ ->
            val textCount = "${text?.length}" + getText(R.string.limit_post_title)
            binding.tvCountTitleWritePost.text = textCount
            isCompletable()
        }
        binding.etDescriptionWritePost.doOnTextChanged { text, _, _, _ ->
            val textCount = "${text?.length}" + getText(R.string.limit_post_description)
            binding.tvCountDescriptionWritePost.text = textCount
            isCompletable()
        }
        binding.etContactsWritePost.doOnTextChanged { text, _, _, _ ->
            val textCount = "${text?.length}" + getText(R.string.limit_post_contacts)
            binding.tvCountContactsWritePost.text = textCount
            isCompletable()
        }
        binding.etPetNameWritePost.doOnTextChanged { _, _, _, _ ->
            isCompletable()
        }
        binding.etPetSpeciesWritePost.doOnTextChanged { _, _, _, _ ->
            isCompletable()
        }
        binding.ivStartDateWritePost.setOnClickListener {
            SelectDateDialog(
                context = this,
                minDate = deadline,
                maxDate = maxDate
            ) {
                minDate = it.toDate()
                val date = it
                binding.tvStartDateWritePost.text = date
            }.callDialog()
            isCompletable()
        }
        binding.ivEndDateWritePost.setOnClickListener {
            SelectDateDialog(
                context = this,
                minDate = minDate ?: deadline
            ) {
                maxDate = it.toDate()
                val date = it
                binding.tvEndDateWritePost.text = date
            }.callDialog()
            isCompletable()
        }
        binding.ivDeadlineWritePost.setOnClickListener {
            SelectDateDialog(
                context = this,
                maxDate = minDate
            ) {
                deadline = it.toDate()
                val date = it
                binding.tvCurDeadlineWritePost.text = date
            }.callDialog()
            isCompletable()
        }
        binding.btnDeleteImageWritePost.setOnClickListener {
            if (imageListAdapter.itemCount == 0) return@setOnClickListener
            binding.vpImageWritePost.isUserInputEnabled = false
            imageListAdapter.deletePostImageList(binding.vpImageWritePost.currentItem)
            binding.indicatorImageWritePost.refreshDots()
            binding.vpImageWritePost.isUserInputEnabled = true
            if (imageListAdapter.itemCount == 0) {
                it.visibility = INVISIBLE
                binding.vpImageWritePost.visibility = INVISIBLE
                binding.cvEmptyImage.visibility = VISIBLE
            }
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
            writePostViewModel.sendImage(
                PostImageParam(it, imageListAdapter.getPostImageList())
            )
        }
        writePostViewModel.sendImageSuccessEvent.observe(this) {
            if (isEditMode) finish()
            else {
                val intent = Intent(this, PostDetailActivity::class.java)
                intent.putExtra(KEY_POST_ID, postId)
                startActivity(intent)
                finish()
            }
        }
        writePostViewModel.badRequestEvent.observe(this) {
            makeToast(getString(R.string.bad_request))
        }
        writePostViewModel.retryEvent.observe(this) {
            makeToast(getString(R.string.try_it_later))
        }
        writePostViewModel.needToLoginEvent.observe(this) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
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
            setDeadLine(intent.getStringExtra(IntentKeys.KEY_DEADLINE)!!)
            binding.etDescriptionWritePost.setText(intent.getStringExtra(IntentKeys.KEY_DESCRIPTION))
            binding.etContactsWritePost.setText(intent.getStringExtra(IntentKeys.KEY_CONTACTS))
            imageListAdapter.setPostImageList(
                intent.getStringArrayListExtra(IntentKeys.KEY_POST_IMAGE_LIST)!!
            )
            binding.indicatorImageWritePost.setViewPager2(binding.vpImageWritePost)
            binding.etPetNameWritePost.setText(intent.getStringExtra(IntentKeys.KEY_PET_NAME))
            binding.etPetSpeciesWritePost.setText(intent.getStringExtra(IntentKeys.KEY_PET_SPECIES))
            if (intent.getStringExtra(IntentKeys.KEY_PET_SEX) == "MALE") binding.rgGenderWritePost.check(
                R.id.rb_male_write_post
            ) else binding.rgGenderWritePost.check(R.id.rb_female_write_post)
            binding.btnWritePost.text = getString(R.string.complete_edit)
        } else {
            binding.tvAppBarWritePost.text = getString(R.string.title_write_post)
            binding.btnWritePost.text = getString(R.string.complete_write)
        }
    }

    private fun isCompletable(): Boolean {
        if (binding.rgAnimalCategoriesWritePost.checkedRadioButtonId == -1) return false
        if (binding.etTitleWritePost.text.isEmpty()) return false
        if (binding.tvStartDateWritePost.text.equals(getString(R.string.hyphen))) return false
        if (binding.tvEndDateWritePost.text.equals(getString(R.string.hyphen))) return false
        if (binding.tvCurDeadlineWritePost.equals(getString(R.string.hyphen))) return false
        if (binding.etDescriptionWritePost.text.isEmpty()) return false
        if (binding.etContactsWritePost.text.isEmpty()) return false
        if (imageListAdapter.itemCount == 0) return false
        if (binding.etPetNameWritePost.text.isEmpty()) return false
        if (binding.etPetSpeciesWritePost.text.isEmpty()) return false
        binding.btnWritePost.isEnabled = true
        return true
    }

    private fun checkCategory(animalType: AnimalType) {
        when (animalType) {
            AnimalType.MAMMEL ->
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

    private fun setDeadLine(date: String) {
        binding.tvCurDeadlineWritePost.text = date
        deadline = date.toDate()
    }

    @SuppressLint("CheckResult")
    private fun getImage() {
        TedRxImagePicker.with(this)
            .max(5 - imageListAdapter.itemCount, getString(R.string.max_is_five))
            .startMultiImage()
            .subscribe({ uriList ->
                uriList.map {
                    imageListAdapter.addPostImageList(File(getRealPathFromURI(it)!!))
                }
                if (imageListAdapter.itemCount > 0) {
                    binding.cvEmptyImage.visibility = INVISIBLE
                    binding.vpImageWritePost.visibility = VISIBLE
                    binding.btnDeleteImageWritePost.visibility = VISIBLE
                }
                binding.indicatorImageWritePost.refreshDots()
                isCompletable()
            }, Throwable::printStackTrace)
    }

    private fun getRealPathFromURI(uri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(this, uri, proj, null, null, null)
        val cursor: Cursor = loader.loadInBackground()!!
        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
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
            petSpecies = binding.etPetSpeciesWritePost.text.toString(),
            petSex = when (binding.rgGenderWritePost.checkedRadioButtonId) {
                R.id.rb_male_write_post -> Sex.MALE
                R.id.rb_female_write_post -> Sex.FEMALE
                else -> Sex.MALE
            },
            animalType = binding.rgAnimalCategoriesWritePost.checkedRadioButtonId.toAnimalType()
        )
        if (isEditMode) writePostViewModel.fixPost(FixedPostParam(postId, postParam))
        else writePostViewModel.writePost(postParam)
        binding.btnWritePost.isEnabled = false
    }
}