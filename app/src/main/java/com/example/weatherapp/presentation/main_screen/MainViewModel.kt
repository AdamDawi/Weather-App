package com.example.weatherapp.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.use_case.GetUserLocationUseCase
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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
    private val getUserLocationUseCase: GetUserLocationUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    @OptIn(SavedStateHandleSaveableApi::class)
    var currentDate by savedStateHandle.saveable {
        mutableStateOf("")
    }
        private set

    val state: StateFlow<Resource<Weather>> = flow<Resource<Weather>> {
        while (true) {
            Timber.d("Fetching current location and weather")

            getUserLocationUseCase()
                .onEach {
                    currentDate = getCurrentDateWithFormat()
                    savedStateHandle[SAVED_STATE_DATE] = currentDate
                }.collect { locationResource ->
                    when (locationResource) {
                        is Resource.Success -> {
                            val location = locationResource.data
                            if (location == null) {
                                emit(Resource.Error("Location not found"))
                            } else {
                                getWeatherUseCase(
                                    location.longitude,
                                    location.latitude
                                ).collect { weatherResource ->
                                    emit(weatherResource)
                                }
                            }
                        }
                        is Resource.Error -> {
                            emit(Resource.Error(locationResource.message ?: "Error occurred"))
                        }
                        is Resource.Loading -> {}
                    }
                }
            delay(5000)
        }
    }.catch { e ->
        emit(Resource.Error(e.localizedMessage ?: "An unexpected error when fetching weather"))
    }.onCompletion {
        Timber.d("Weather and Location flow completed")
    }.stateIn(
        scope = viewModelScope,
        initialValue = Resource.Loading(),
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    companion object {
        private const val SAVED_STATE_DATE = "SAVED_STATE_DATE"
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateWithFormat(): String {
        return SimpleDateFormat("HH:mm:ss").format(Date())
    }
}


