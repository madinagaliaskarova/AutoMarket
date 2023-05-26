package com.example.technicaltaskforitsurf.ui.fragment.newcar

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.notesm.R
import com.example.notesm.databinding.FragmentAddNewCarBinding
import com.example.technicaltaskforitsurf.data.localdb.preferences.SettingsSharedPreferences
import com.example.technicaltaskforitsurf.ui.alertdialog.AlertDialogFragment
import com.example.technicaltaskforitsurf.ui.base.BaseFragment
import com.example.technicaltaskforitsurf.ui.extensions.hasPermissionCheck
import com.example.technicaltaskforitsurf.ui.fragment.newcar.adapter.ImageGalleryAdapter
import com.example.technicaltaskforitsurf.ui.model.CarsModelUI
import com.markodevcic.peko.PermissionResult
import com.markodevcic.peko.requestPermissionsAsync
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


const val maxFreeViewedCars = 3
const val maxFreeUploadedCars = 2

@AndroidEntryPoint
class AddNewCarFragment :
    BaseFragment<FragmentAddNewCarBinding, AddNewCarViewModel>(R.layout.fragment_add_new_car) {

    override val binding by viewBinding(FragmentAddNewCarBinding::bind)
    override val viewModel: AddNewCarViewModel by viewModels()

    private val imageGalleryAdapter = ImageGalleryAdapter(this::onItemClick)
    private val args by navArgs<AddNewCarFragmentArgs>()

    @Inject
    lateinit var preferences: SettingsSharedPreferences

    override fun initialize() {
        initImageGalleryAdapter()
    }

    private fun initImageGalleryAdapter() {
        binding.recycler.adapter = imageGalleryAdapter
        args.photoList?.toList()?.let {
            viewModel.addLocalUploadPhoto(it)
        }
    }

    override fun constructListeners() {
        initBtnAdd()
    }

    private fun initBtnAdd() {
        binding.btnAddCar.setOnClickListener {
            when {
                preferences.isSubscribed -> {
                    insertNewCarToDatabase()
                }

                preferences.uploadedCars < maxFreeUploadedCars -> {
                    insertNewCarToDatabase()
                }

                else -> {
                    val dialog = AlertDialogFragment.newInstance("Истекло", "Купите подписку !")
                    dialog.show(childFragmentManager, "alert")
                }
            }
        }
    }

    private fun insertNewCarToDatabase() {
        val releaseYear = binding.editTextYear.text.toString()
        val engineValue = binding.editTextEngineCapacity.text.toString()
        val carName = binding.editTextCarName.text.toString()
        val imageUrl = imageGalleryAdapter.currentList.getOrNull(1)?.localPath

        if (releaseYear.isBlank() || engineValue.isBlank() || carName.isBlank() || imageUrl == null) {
            Toast.makeText(
                requireContext(),
                "Пожалуйста, заполните все поля",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.addNewCar(
                CarsModelUI(
                    releaseYear = releaseYear.toInt(),
                    engineValue = engineValue.toInt(),
                    dateJoined = System.currentTimeMillis(),
                    imageUrl = imageUrl,
                    carName = carName,
                )
            )
            preferences.uploadedCars = preferences.uploadedCars + 1
            Toast.makeText(
                requireContext(),
                "Success",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun launchObservers() {
        loadPicturesFromGallery()
    }

    private fun loadPicturesFromGallery() {
        safeFlowGather(Lifecycle.State.RESUMED) {
            viewModel.imageGalleryPhotosState.collectLatest {
                imageGalleryAdapter.submitMyList(it)
            }
        }
    }

    private fun onItemClick() {
        requestImage()
    }

    private fun requestImage() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.CAMERA)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        }
        if (hasPermissionCheck(permissions)) {
            openPicker()
        } else {
            safeFlowGather {
                when (requestPermissionsAsync(*permissions)) {
                    is PermissionResult.Granted -> {}
                    is PermissionResult.Denied.JustDenied -> {}
                    is PermissionResult.Denied.NeedsRationale -> {}
                    is PermissionResult.Denied.DeniedPermanently -> {}
                    PermissionResult.Cancelled -> {}
                }
            }
        }
    }

    private fun openPicker() {
        findNavController().navigate(
            R.id.action_addNewCarFragment_to_photoPickerFragment
        )
    }
}