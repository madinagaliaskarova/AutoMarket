package com.example.technicaltaskforitsurf.domain.usecase

import com.example.technicaltaskforitsurf.domain.model.CarsModelDomain
import com.example.technicaltaskforitsurf.domain.repository.CarsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCarUseCase @Inject constructor(private val carRepository: CarsRepository) {

    operator fun invoke(id: Int): Flow<CarsModelDomain> {
        return carRepository.getCar(id)
    }
}