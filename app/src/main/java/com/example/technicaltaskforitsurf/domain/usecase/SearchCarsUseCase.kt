package com.example.technicaltaskforitsurf.domain.usecase

import com.example.technicaltaskforitsurf.domain.model.CarsModelDomain
import com.example.technicaltaskforitsurf.domain.repository.CarsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCarsUseCase @Inject constructor (private val carsRepository: CarsRepository) {

    operator fun invoke(query: String): Flow<List<CarsModelDomain>> {
        return carsRepository.search(query)
    }
}