package com.example.weatherapp.presentation.main_screen

import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.common.Resource
import com.example.weatherapp.presentation.main_screen.components.WeatherContent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@SuppressLint("SuspiciousIndentation", "SimpleDateFormat")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentDate = viewModel.currentDate

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
        //when the app get launched for the first time
        LaunchedEffect(Unit){
            locationPermissions.launchMultiplePermissionRequest()
        }

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(state) {
                is Resource.Loading ->{
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(40.dp),
                        color = Color.Black
                    )
                }
                is Resource.Error ->  { Text(text = state.message!!) }
                is Resource.Success -> {
                    WeatherContent(
                        currentDate = currentDate,
                        weatherData = state.data!!,
                        location = Geocoder(LocalContext.current)
                            .getFromLocation(
                                state.data!!.latitude,
                                state.data!!.longitude,
                                1
                            )?.get(0)?.locality ?: ""
                    )
                }
            }
        }
}
