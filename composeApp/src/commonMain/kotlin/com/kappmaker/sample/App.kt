package com.kappmaker.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappmaker.devicepreview.preview.DeviceWithConfigurationView
import com.kappmaker.devicepreview.preview.SimulatedDeviceThemeIsDark
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(size: WindowSizeClass) {
    DeviceWithConfigurationView(size = size) {
        val isDark by SimulatedDeviceThemeIsDark.current //Should be replaced with a real theme change
        MaterialTheme(colorScheme = if (isDark) darkColorScheme() else lightColorScheme()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                items(50) {
                    Text(
                        "Item #$it",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }

}

