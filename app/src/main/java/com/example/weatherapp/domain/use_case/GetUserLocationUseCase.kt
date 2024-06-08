package com.example.weatherapp.domain.use_case

import com.example.weatherapp.common.ResourceLocation
import com.example.weatherapp.data.location.LocationTracker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetUserLocationUseCase@Inject constructor(
    private val repository: LocationTracker
) {

    operator fun invoke(): Flow<ResourceLocation> = flow{
        Timber.tag("Flow").d("Fetching current location")
        try {
            val result = repository.getCurrentLocation()
            emit(ResourceLocation.Success(result))
        }catch (e: Exception){
            emit(ResourceLocation.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }
}