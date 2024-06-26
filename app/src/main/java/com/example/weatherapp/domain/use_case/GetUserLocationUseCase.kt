package com.example.weatherapp.domain.use_case

import android.location.Location
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber
import javax.inject.Inject

open class GetUserLocationUseCase@Inject constructor(
    private val repository: LocationRepository
) {
    open operator fun invoke(): Flow<Resource<Location>> = flow{
        Timber.d("Fetching current location")
        try {
            val result = repository.getCurrentLocation()
            emit(Resource.Success(result))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error when fetching location"))
        }
    }.onCompletion {
        Timber.d("Location fetch completed")
    }
}