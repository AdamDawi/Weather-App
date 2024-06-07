package com.example.weatherapp.presentation.main_screen

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    getWeatherUseCase: GetWeatherUseCase,
    private val locationTracker: LocationTracker
): ViewModel(){

    var currentLocation by mutableStateOf<Location?>(null)

    private var latitude: Double = currentLocation?.latitude ?: 0.0
    private var longitude: Double = currentLocation?.longitude ?: 0.0

    private var _state = getWeatherUseCase(latitude, longitude)
        .onCompletion {
            Timber.tag("Flow").d("Flow has completed.")
        }
        .onStart {
            emit(Resource.Loading)
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = Resource.Loading,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
        )
    val state: StateFlow<Resource> = _state

    fun getCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            currentLocation = locationTracker.getCurrentLocation() // Location
            Timber.e(currentLocation.toString())
        }
    }
}