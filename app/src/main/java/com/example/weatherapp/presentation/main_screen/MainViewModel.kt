package com.example.weatherapp.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Resource
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
): ViewModel(){

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state


    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch{
            getWeatherUseCase(lat, lon)
                .collect {result ->
                when(result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            weather = result.data.toWeather(),
                            isLoading = false
                            )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}