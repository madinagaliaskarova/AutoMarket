package com.example.technicaltaskforitsurf.ui.fragment.picker

import android.content.Context
import android.provider.MediaStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.notesm.R
import com.example.notesm.databinding.FragmentPhotoPickerBinding
import com.example.technicaltaskforitsurf.ui.base.BaseFragment
import com.example.technicaltaskforitsurf.ui.fragment.newcar.AddNewCarViewModel
import com.example.technicaltaskforitsurf.ui.model.PhotoUploadResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoPickerFragment :
    BaseFragment<FragmentPhotoPickerBinding, AddNewCarViewModel>(R.layout.fragment_photo_picker) {

    override val viewModel by viewModels<AddNewCarViewModel>()
    override val binding by viewBinding(FragmentPhotoPickerBinding::bind)

    private val pickerAdapter by lazy {
        PhotoPickerAdapter(viewModel.imageGalleryPhotosState.value)
    }

    override fun initialize() {
        initPickerAdapter()
    }

    private fun initPickerAdapter() {
        binding.rvImages.adapter = pickerAdapter
        viewModel.viewModelScope.launch {
            viewModel.updateAllPhotosPhoneState(getAllPhotosFromDevice(requireContext()))
        }
    }

    override fun constructListeners() {
        setupBtnAdd()
        setupBtnBack()
    }

    private fun setupBtnAdd() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(
                PhotoPickerFragmentDirections.actionPhotoPickerFragmentToAddNewCarFragment(
                    pickerAdapter.getSelectedPhotos().toTypedArray()
                )
            )
        }
    }

    private fun setupBtnBack() {
        binding.imExit.setOnClickListener {
            findNavController().navigate(
                PhotoPickerFragmentDirections.actionPhotoPickerFragmentToAddNewCarFragment(
                    pickerAdapter.getSelectedPhotos().toTypedArray()
                )
            )
        }
    }

    override fun launchObservers() {
        fetchAllPhotosPhone()
    }

    private fun fetchAllPhotosPhone() {
        safeFlowGather {
            viewModel.allPhotosPhoneState.collectLatest {
                pickerAdapter.submitList(it)
            }
        }
    }

    private fun getAllPhotosFromDevice(context: Context): List<PhotoUploadResult> {
        val galleryPhotos = mutableListOf<PhotoUploadResult>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA
        )
        val selection = "${MediaStore.Images.Media.SIZE} > 0"
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        val queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor = context.contentResolver.query(queryUri, projection, selection, null, sortOrder)
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (it.moveToNext()) {
                val data = it.getString(dataColumn)
                val photo = PhotoUploadResult(localPath = data, id = idColumn)
                galleryPhotos.add(photo)
            }
        }
        for (selectedPhoto in viewModel.imageGalleryPhotosState.value) {
            val index = galleryPhotos.indexOfFirst { it.localPath == selectedPhoto.localPath }
            if (index != -1) {
                galleryPhotos[index] = selectedPhoto
            }
        }
        return galleryPhotos
    }
}