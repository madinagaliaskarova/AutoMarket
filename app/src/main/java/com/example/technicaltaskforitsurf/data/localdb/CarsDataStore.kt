package com.example.technicaltaskforitsurf.data.localdb

import com.example.technicaltaskforitsurf.data.localdb.room.dao.CarsDao
import com.example.technicaltaskforitsurf.data.model.CarsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CarsDataStore {
    suspend fun addCar(car: CarsModel): Long
    fun getCars(): Flow<List<CarsModel>>
    fun searchCars(query: String): Flow<List<CarsModel>>
}

class RoomCarsDataStore @Inject constructor(private val carsDao: CarsDao) : CarsDataStore {
    override suspend fun addCar(car: CarsModel) = carsDao.insert(car)
    override fun getCars() = carsDao.getCars()
    override fun searchCars(query: String) = carsDao.search(query)
}