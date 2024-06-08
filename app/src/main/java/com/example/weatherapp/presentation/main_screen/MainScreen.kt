package com.example.weatherapp.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.common.Resource
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
        LaunchedEffect(true){
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
                is Resource.Error -> Text(text = (state as Resource.Error).message)
                is Resource.Success -> {
                    Text(
                        text = "lastUpdateTime: ${currentDate.value}",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(text = "latitude: ${(state as Resource.Success).data.latitude}, longitude: ${(state as Resource.Success).data.longitude}")
                    Text(text = "${(state as Resource.Success).data.current}")
                }
            }
        }
}