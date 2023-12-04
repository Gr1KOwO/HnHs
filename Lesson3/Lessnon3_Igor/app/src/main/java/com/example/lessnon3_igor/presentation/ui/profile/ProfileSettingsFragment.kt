package com.example.lessnon3_igor.presentation.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.FragmentProfileBinding
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProfile
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import com.example.lessnon3_igor.presentation.exception.getError
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ProfileSettingsFragment : Fragment() {

    private object BottomSheetActions {
        const val AVATAR = "avatar"
        const val OCCUPATION = "occupation"
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
        private const val STORAGE_PERMISSION_REQUEST_CODE = 101
        private const val CAMERA_REQUEST_CODE = 102
        private const val GALLERY_REQUEST_CODE = 103
    }

    private lateinit var binding:FragmentProfileBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        ProfileViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.silver_chalice)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onLoadDataProfile()
        clickEvent()
    }


    private fun onLoadDataProfile()
    {
        viewModel.profileLiveData.observe(viewLifecycleOwner){ response ->
            when (response) {
                is ResponseStates.Success -> {
                    initializeProfile(response.data)
                }
                is ResponseStates.Loading -> {

                }
                is ResponseStates.Failure -> {
                    error(response.e.getError().toString())
                }
            }
        }
        viewModel.executeProfile()
    }

    private fun initializeProfile(profile:ResponseProfile)
    {
        binding.nameText.setText(profile.name)
        binding.surnameText.setText(profile.surname)
        binding.kindOfActivityText.setText(profile.occupation)
        Glide.with(binding.profileImg)
            .load(profile.avatarId)
            .into(binding.profileImg)
        binding.acceptBtn.setText(binding.root.resources.getText(R.string.refreshDataProfile).toString())

        setFragmentResultListener("result") { _, bundle ->
            val selectedVariant = bundle.getString("selectedVariant")
            when(bundle.getString("action"))
            {
                BottomSheetActions.AVATAR->{
                    when (selectedVariant) {
                        "Сделать снимок" -> {
                            checkCameraPermission()
                        }
                        "Выбрать из галереи" -> {
                            checkStoragePermission()
                        }
                    }
                }

                BottomSheetActions.OCCUPATION->{
                    binding.kindOfActivityText.setText(selectedVariant)
                    binding.anotherKindOfActivityLayout.isVisible = selectedVariant=="Другое"
                }
            }
        }
    }

    private fun clickEvent()
    {
        binding.profileImg.setOnClickListener {
            val action =
                ProfileSettingsFragmentDirections.actionFragmentProfileToChosenBottomSheetFragment2(
                    binding.root.resources.getStringArray(R.array.changing_the_avatar),BottomSheetActions.AVATAR
                )
            findNavController().navigate(action)
        }

        binding.acceptBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.kindOfActivityLayout.setEndIconOnClickListener{
            val action=
                ProfileSettingsFragmentDirections.actionFragmentProfileToChosenBottomSheetFragment2(
                    binding.root.resources.getStringArray(R.array.occupationList),BottomSheetActions.OCCUPATION
                )
            findNavController().navigate(action)
        }
        binding.topBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.errorCamera),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            STORAGE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.errorStorage),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    Glide.with(binding.profileImg)
                        .load(imageBitmap)
                        .transform(
                            MultiTransformation(
                                CircleCrop()
                            )
                        )
                        .placeholder(R.drawable.img_profile)
                        .into(binding.profileImg)
                }
            }
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val imageUri = data.data
                    Glide.with(binding.profileImg)
                        .load(imageUri)
                        .transform(
                            MultiTransformation(
                                CircleCrop()
                            )
                        )
                        .placeholder(R.drawable.img_profile)
                        .into(binding.profileImg)
                }
            }
        }
    }
}