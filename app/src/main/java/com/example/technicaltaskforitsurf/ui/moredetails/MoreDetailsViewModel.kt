package com.example.technicaltaskforitsurf.ui.moredetails


import androidx.lifecycle.viewModelScope
import com.example.technicaltaskforitsurf.domain.usecase.GetCarUseCase
import com.example.technicaltaskforitsurf.ui.base.BaseViewModel
import com.example.technicaltaskforitsurf.ui.model.CarsModelUI
import com.example.technicaltaskforitsurf.ui.model.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarDetailViewModel @Inject constructor(
    private val getCarUseCase: GetCarUseCase,
) : BaseViewModel() {

    private val _carState = MutableStateFlow<CarsModelUI?>(null)
    val carState: StateFlow<CarsModelUI?> get() = _carState.asStateFlow()

    fun getCar(id: Int) {
        viewModelScope.launch {
            _carState.value = getCarUseCase(id).first().toUI()
        }
    }
}