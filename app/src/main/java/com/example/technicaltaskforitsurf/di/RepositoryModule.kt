package com.example.technicaltaskforitsurf.di

import com.example.technicaltaskforitsurf.data.repository.CarsRepositoryImpl
import com.example.technicaltaskforitsurf.domain.repository.CarsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCarsRepository(carsRepositoryImpl: CarsRepositoryImpl): CarsRepository

}