package com.example.weatherapp.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardForDetail(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    unit: String?
) {
   Box(modifier = modifier
       .background(
           color = Color.Gray,
           shape = RoundedCornerShape(12.dp)
       )
       .size(160.dp, 80.dp)
       .padding(16.dp)
   ){
         Column(
             modifier = Modifier.fillMaxSize(),
             verticalArrangement = Arrangement.SpaceEvenly
         ) {
             Text(
                 text = title,
                 fontSize = 10.sp
             )
             Text(
                 text = "$value ${unit.orEmpty()}",
                 fontSize = 18.sp,
                 fontWeight = FontWeight.SemiBold
             )
         }
   }
}

@Preview
@Composable
private fun CardForDetailPreview() {
    CardForDetail(
        title = "Rain",
        value = "2.0",
        unit = null
    )
}