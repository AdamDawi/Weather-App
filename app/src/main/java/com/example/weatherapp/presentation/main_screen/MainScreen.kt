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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

@SuppressLint("SuspiciousIndentation", "SimpleDateFormat")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentDate = viewModel.currentDate
    val context = LocalContext.current
    val geocoder = remember { Geocoder(context, Locale.getDefault()) }
    val address = remember { mutableStateOf("") }

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

    LaunchedEffect(state) {
        if (state is Resource.Success) {
            val latitude = state.data!!.latitude
            val longitude = state.data!!.longitude
            withContext(Dispatchers.IO) {
                try {
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    if (addresses?.isNotEmpty() == true) {
                        val addressLine = addresses[0].let { "${it.countryName}, ${it.locality ?: ""}" }
                        address.value = addressLine
                    } else {
                        address.value = "Address not found"
                    }
                } catch (e: Exception) {
                    address.value = "Geocoding failed: ${e.message}"
                }
            }
        }
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
                        location =  address.value
                    )
                }
            }
        }
}
