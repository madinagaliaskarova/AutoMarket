package com.example.technicaltaskforitsurf.ui.fragment.newcar

import androidx.lifecycle.viewModelScope
import com.example.technicaltaskforitsurf.domain.model.CarsModelDomain
import com.example.technicaltaskforitsurf.domain.usecase.InsertCarUseCase
import com.example.technicaltaskforitsurf.ui.base.BaseViewModel
import com.example.technicaltaskforitsurf.ui.model.CarsModelUI
import com.example.technicaltaskforitsurf.ui.model.PhotoUploadResult
import com.example.technicaltaskforitsurf.ui.model.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewCarViewModel @Inject constructor(
    private val insertCarUseCase: InsertCarUseCase
) : BaseViewModel() {

    private val _newCars = MutableStateFlow<List<CarsModelDomain>>(emptyList())
    val newCar: StateFlow<List<CarsModelDomain>> = _newCars.asStateFlow()

    private val _imageGalleryPhotosState =
        MutableStateFlow<List<PhotoUploadResult>>(emptyList())
    val imageGalleryPhotosState = _imageGalleryPhotosState.asStateFlow()

    private val _allPhotosPhoneState = MutableStateFlow<List<PhotoUploadResult>>(emptyList())
    val allPhotosPhoneState = _allPhotosPhoneState.asStateFlow()

    fun updateAllPhotosPhoneState(newList: List<PhotoUploadResult>) {
        _allPhotosPhoneState.value = newList
    }

    fun addLocalUploadPhoto(photoList: List<PhotoUploadResult>) {
        _imageGalleryPhotosState.value = photoList
    }

    fun addNewCar(cars: CarsModelUI) {
        viewModelScope.launch(Dispatchers.Main) {
            insertCarUseCase(cars.toDomain())
        }
    }

}







