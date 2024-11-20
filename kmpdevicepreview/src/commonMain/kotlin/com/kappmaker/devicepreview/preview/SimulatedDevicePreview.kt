package com.kappmaker.devicepreview.preview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.kappmaker.devicepreview.core.DeviceConfiguration
import com.kappmaker.devicepreview.core.SimulatedDevice
import com.kappmaker.devicepreview.devices.android.Pixel6
import kotlin.math.min

public val SimulatedDeviceThemeIsDark: ProvidableCompositionLocal<MutableState<Boolean>> =
    compositionLocalOf { mutableStateOf(false) }


@Composable
public fun SimulatedDevicePreview(
    simulatedDevice: SimulatedDevice = SimulatedDevice(
        device = Pixel6(),
        configuration = DeviceConfiguration(isPortrait = true, isDarkMode = false)
    ),
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val isDarkState =
        remember(simulatedDevice.configuration.isDarkMode) { mutableStateOf(simulatedDevice.configuration.isDarkMode) }

    CompositionLocalProvider(SimulatedDeviceThemeIsDark provides isDarkState) {

        BoxWithConstraints(
            modifier = modifier.padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            val containerWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
            val containerHeightPx = with(LocalDensity.current) { maxHeight.toPx() }


            val deviceScreenWidth = simulatedDevice.currentScreenSize.width
            val deviceScreenHeight = simulatedDevice.currentScreenSize.height
            val deviceDensity = simulatedDevice.currentScreenSize.density
            val deviceAspectRatio = deviceScreenWidth / deviceScreenHeight

            val scaleFactor = min(
                containerWidthPx / deviceScreenWidth,
                containerHeightPx / deviceScreenHeight
            )

            val scaledDensity = deviceDensity * scaleFactor


            val shape = RoundedCornerShape(8.dp)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(deviceAspectRatio)
                    .border(
                        shape = shape,
                        border = BorderStroke(2.dp, Color.Black)
                    )
                    .clip(shape)

            ) {
                CompositionLocalProvider(
                    LocalDensity provides Density(density = scaledDensity, fontScale = 1f)
                ) {
                    Box(Modifier.fillMaxSize()) {
                        content()
                    }
                }

            }

        }
    }

}