package com.example.technicaltaskforitsurf.data.localdb.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.technicaltaskforitsurf.data.model.CarsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: CarsModel): Long

    @Query("SELECT * FROM cars")
    fun getCars(): Flow<List<CarsModel>>

    @Query("SELECT * FROM cars WHERE carName LIKE :query")
    fun search(query: String): Flow<List<CarsModel>>

    @Query("SELECT * FROM cars WHERE id = :id")
    fun getById(id: Int): Flow<CarsModel>
}