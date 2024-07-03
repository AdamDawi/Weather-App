package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.data.location.LocationTracker
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.api.FakeLocationTrackerSuccess
import com.example.weatherapp.data.remote.api.FakeSuccessApi
import com.example.weatherapp.data.repository.LocationRepositoryImpl
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.LocationRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.use_case.GetUserLocationUseCase
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi{
        return FakeSuccessApi()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository{
        return WeatherRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(locationTracker: LocationTracker): LocationRepository{
        return LocationRepositoryImpl(locationTracker)
    }

    @Provides
    @Singleton
    fun provideGetWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase{
        return GetWeatherUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun providesLocationTracker(
    ): LocationTracker = FakeLocationTrackerSuccess()

    @Provides
    @Singleton
    fun providesGetUserLocationUseCase(
        repository: LocationRepository
    ): GetUserLocationUseCase{
        return GetUserLocationUseCase(repository)
    }
}