package com.example.technicaltaskforitsurf.data.localdb.room

import android.content.Context
import androidx.room.Room
import com.example.technicaltaskforitsurf.data.localdb.room.dao.CarsDao

class RoomClient {

    fun provideRoom(context: Context) = Room
        .databaseBuilder(context, AppDatabase::class.java, "appDatabase")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    fun provideCarsDao(appDatabase: AppDatabase): CarsDao = appDatabase.carsDao()
}