package com.example.technicaltaskforitsurf.domain.repository

import com.example.technicaltaskforitsurf.domain.model.CarsModelDomain
import kotlinx.coroutines.flow.Flow

interface CarsRepository {

    suspend fun addPhoto(addPhoto: CarsModelDomain): Long
    fun getCars(): Flow<List<CarsModelDomain>>
    fun search(query: String): Flow<List<CarsModelDomain>>
    suspend fun insert(carsModelDomain: CarsModelDomain)
    fun getCar(id: Int): Flow<CarsModelDomain>
}


