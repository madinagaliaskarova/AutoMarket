package com.example.technicaltaskforitsurf.data.localdb.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.technicaltaskforitsurf.data.localdb.room.dao.CarsDao
import com.example.technicaltaskforitsurf.data.model.CarsModel

@Database(
    entities = [CarsModel::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun carsDao(): CarsDao
}