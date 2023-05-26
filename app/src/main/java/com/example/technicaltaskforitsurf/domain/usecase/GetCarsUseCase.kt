package com.example.technicaltaskforitsurf.domain.usecase

import com.example.technicaltaskforitsurf.domain.model.CarsModelDomain
import com.example.technicaltaskforitsurf.domain.repository.CarsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCarsUseCase @Inject constructor (private val carsRepository: CarsRepository) {

    operator fun invoke(): Flow<List<CarsModelDomain>> = carsRepository.getCars()
}