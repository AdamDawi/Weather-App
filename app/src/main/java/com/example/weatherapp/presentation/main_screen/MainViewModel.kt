package com.example.weatherapp.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Resource
import com.example.weatherapp.common.ResourceLocation
import com.example.weatherapp.domain.use_case.GetUserLocationUseCase
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase
): ViewModel(){
    private val _currentDate = mutableStateOf("")
    val currentDate: State<String> = _currentDate

    val state: StateFlow<Resource> = flow {
        while (true) {
            Timber.tag("Flow").d("Fetching current location and weather")
            try {
                getUserLocationUseCase().onEach {
                    _currentDate.value = getCurrentDate()
                }.collect { locationResource ->
                    when (locationResource) {
                        is ResourceLocation.Success -> {
                            val location = locationResource.data
                            getWeatherUseCase(
                                location?.longitude ?: 0.0,
                                location?.latitude ?: 0.0
                            ).collect { weatherResource ->
                                emit(weatherResource)
                            }
                        }

                        is ResourceLocation.Error -> {
                            emit(Resource.Error(locationResource.message))
                        }

                        ResourceLocation.Loading -> {
                            emit(Resource.Loading)
                        }
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
            }
            delay(5000)
        }
    }.onCompletion {
        Timber.tag("Flow").d("Weather and Location flow completed")
    }.stateIn(
        scope = viewModelScope,
        initialValue = Resource.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String{
        return SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(Date())
    }
}


