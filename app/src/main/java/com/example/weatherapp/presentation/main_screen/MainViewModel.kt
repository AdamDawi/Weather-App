package com.example.weatherapp.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
): ViewModel(){

    private val _state = mutableStateOf("")
    val state: State<String> = _state

    init {
        viewModelScope.launch {
            _state.value = getWeatherUseCase(
                51.9812,
                23.3772
            ).temperature_2m.toString()
        }
    }
}