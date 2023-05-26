package com.example.technicaltaskforitsurf.di

import android.content.Context
import android.content.SharedPreferences
import com.example.technicaltaskforitsurf.data.localdb.preferences.SettingsSharedPreferences
import com.example.technicaltaskforitsurf.data.localdb.room.AppDatabase
import com.example.technicaltaskforitsurf.data.localdb.room.RoomClient
import com.example.technicaltaskforitsurf.data.localdb.room.dao.CarsDao
import com.example.technicaltaskforitsurf.data.repository.CarsRepositoryImpl
import com.example.technicaltaskforitsurf.domain.repository.CarsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideCarsDatabase(
        @ApplicationContext context: Context
    ) = RoomClient().provideRoom(context)

    @Singleton
    @Provides
    fun provideCarsDao(appDatabase: AppDatabase) = RoomClient().provideCarsDao(appDatabase)

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSettingsPreferencesManager(sharedPreferences: SharedPreferences) =
        SettingsSharedPreferences(sharedPreferences)
}
