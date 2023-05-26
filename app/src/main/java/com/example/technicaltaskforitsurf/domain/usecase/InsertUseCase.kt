package com.example.technicaltaskforitsurf.domain.usecase

import com.example.technicaltaskforitsurf.domain.model.CarsModelDomain
import com.example.technicaltaskforitsurf.domain.repository.CarsRepository
import javax.inject.Inject

class InsertCarUseCase @Inject constructor(private val repository: CarsRepository) {

    suspend operator fun invoke(carsModelDomain: CarsModelDomain) {
        repository.insert(carsModelDomain)
    }
}