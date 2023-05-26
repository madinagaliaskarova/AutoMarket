package com.example.technicaltaskforitsurf.data.repository

import com.example.technicaltaskforitsurf.data.localdb.room.dao.CarsDao
import com.example.technicaltaskforitsurf.data.model.toData
import com.example.technicaltaskforitsurf.data.model.toDomain
import com.example.technicaltaskforitsurf.domain.model.CarsModelDomain
import com.example.technicaltaskforitsurf.domain.repository.CarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CarsRepositoryImpl @Inject constructor(private val carsDao: CarsDao) : CarsRepository {

    override suspend fun addPhoto(addPhoto: CarsModelDomain): Long {
        return carsDao.insert(addPhoto.toData())
    }

    override fun getCars(): Flow<List<CarsModelDomain>> {
        return carsDao.getCars().map { photoList ->
            photoList.map { it.toDomain() }
        }
    }

    override fun search(query: String): Flow<List<CarsModelDomain>> {
        return carsDao.search(query)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insert(carsModelDomain: CarsModelDomain) {
        carsDao.insert(carsModelDomain.toData())
    }

    override fun getCar(id: Int): Flow<CarsModelDomain> {
        return carsDao.getById(id).map { it.toDomain() }
    }
}