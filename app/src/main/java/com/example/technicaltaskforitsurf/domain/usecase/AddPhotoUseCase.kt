package com.example.technicaltaskforitsurf.domain.usecase

import com.example.technicaltaskforitsurf.domain.model.CarsModelDomain
import com.example.technicaltaskforitsurf.domain.repository.CarsRepository
import javax.inject.Inject

class AddPhotoUseCase @Inject constructor (private val carsRepository: CarsRepository)  {

    suspend operator fun invoke(addPhotoModelDomain: CarsModelDomain): Long = carsRepository.addPhoto(addPhotoModelDomain)
}