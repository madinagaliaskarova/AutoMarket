package com.example.technicaltaskforitsurf.ui.fragment.home

import androidx.lifecycle.viewModelScope
import com.example.technicaltaskforitsurf.domain.usecase.GetCarsUseCase
import com.example.technicaltaskforitsurf.domain.usecase.SearchCarsUseCase
import com.example.technicaltaskforitsurf.ui.base.BaseViewModel
import com.example.technicaltaskforitsurf.ui.model.CarsModelUI
import com.example.technicaltaskforitsurf.ui.model.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchCarsUseCase: SearchCarsUseCase,
    private val getCarsUseCase: GetCarsUseCase
) : BaseViewModel() {

    private val _homeCarsState = MutableStateFlow<List<CarsModelUI>>(emptyList())
    val homeCarsState = _homeCarsState.asStateFlow()

    fun getCars() {
        viewModelScope.launch {
            getCarsUseCase().collectLatest {
                _homeCarsState.value = it.map { it.toUI() }
            }
        }
    }

    fun fetchCarsSearchName(name: String) {
        viewModelScope.launch {
            searchCarsUseCase(name).collectLatest {
                _homeCarsState.value = it.map { it.toUI() }
            }
        }
    }

    init {
        getCars()
    }
}