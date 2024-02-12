package com.ak.paging3.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.ak.paging3.R
import com.ak.paging3.databinding.ActivityUserDetailsBinding
import com.ak.paging3.model.User
import com.ak.paging3.viewmodel.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

/**
 * Created by Ajithkumar Vadamalai on 14/12/23.
 */

@AndroidEntryPoint
class UserDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private val viewModel : UserDetailsViewModel by viewModels()

    private lateinit var callIntent: Intent

    private val permissionsResultCallback = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
        when (it) {
            true -> {
                makeCall()
            }
            false -> {
                showPermissionDialog("Call")
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initApiCall()

        binding.progressDialog.isVisible = true

        viewModel.user.observe(this){
            binding.lytNoData.isVisible = false
           updateUserDetails(it)
        }

        viewModel.error.observe(this){
            binding.progressDialog.isVisible = false
           binding.lytNoData.isVisible = true
        }

        binding.imgArrowBack.setOnClickListener {
            finish()
        }

        binding.txtNoData.setOnClickListener {
            binding.progressDialog.isVisible = true
            initApiCall()
        }


    }

    fun initApiCall(){
        if (intent.hasExtra("UserId")){
            viewModel.getUser(intent.getIntExtra("UserId",1))
        }else{
            viewModel.getUser(1)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updateUserDetails(user: User){
        Glide.with(this)
            .load(user.image)
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_broken_image)
            .into(binding.imgUser)
        binding.txtFullName.text = user.fullName
        binding.txtEmail.text = user.email
        binding.txtMobile.text = user.phone
        binding.txtAddress.text = user.fullHomeAddress
        binding.txtBloodGroup.text = user.bloodGroup
        binding.txtAge.text = user.dob
        binding.txtUniversity.text = user.university
        binding.txtGender.text = user.gender.substring(0, 1).uppercase(Locale.ROOT) + user.gender.substring(1)
            .lowercase(Locale.ROOT)
        binding.txtGender.setCompoundDrawablesWithIntrinsicBounds(if (user.gender == "male") ContextCompat.getDrawable(this,R.drawable.ic_male) else if (user.gender == "female") ContextCompat.getDrawable(this,R.drawable.ic_female)else ContextCompat.getDrawable(this,R.drawable.ic_trans),
            null,null,null)

        binding.txtCompanyName.text = user.company.name
        binding.txtRole.text = user.company.title
        binding.txtDepartment.text = user.company.department
        binding.txtCompanyAddress.text = user.company.fullCompanyAddress
        binding.progressDialog.isVisible = false

        binding.imgCall.setOnClickListener {
            callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:" + user.phone)
            checkCallPermission()
        }

        binding.imgMail.setOnClickListener {
            try {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:" + user.email)
            startActivity(intent)
            }catch (e:Exception){
                Toast.makeText(this,"No Supporting Apps Found!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkCallPermission() {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(Manifest.permission.CALL_PHONE)
        } else {
            makeCall()
        }
    }

    private fun makeCall(){
        try {
            startActivity(callIntent)
        }catch (e:Exception){
            Toast.makeText(this,"No Supporting Apps Found!",Toast.LENGTH_SHORT).show()
        }
    }


}