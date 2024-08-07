package com.example.weatherapp.presentation.main_screen.components

import android.content.res.Configuration
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.common.Constants
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme

@Composable
fun ThemeSwitcher(
    modifier: Modifier = Modifier,
    darkTheme: Boolean,
    size: Dp = 35.dp,
    iconSize: Dp = size/3,
    padding: Dp = 4.dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: () -> Unit
) {
        val offset by animateDpAsState(
            targetValue = if (darkTheme) 0.dp else size,
            animationSpec = animationSpec,
            label = ""
        )

    Box(
        modifier = modifier
            .width(size * 2)
            .height(size)
            .clip(shape = parentShape)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .testTag(Constants.THEME_SWITCHER)
    ){
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    shape = parentShape
                )
        ) {
            Box(
                modifier = Modifier
                    .size(size),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = R.drawable.night_mode),
                    contentDescription = "Night mode icon",
                    tint = if(darkTheme) MaterialTheme.colorScheme.onSecondaryContainer
                    else MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = R.drawable.light_mode),
                    contentDescription = "Light mode icon",
                    tint = if(darkTheme) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ThemeSwitcherDarkThemePreview() {
    WeatherAppTheme {
        Surface {
            ThemeSwitcher(darkTheme = isSystemInDarkTheme()) {}
        }
    }
}