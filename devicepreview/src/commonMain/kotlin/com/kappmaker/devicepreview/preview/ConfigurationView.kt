package com.kappmaker.devicepreview.preview

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kappmaker.devicepreview.core.Device
import com.kappmaker.devicepreview.core.DeviceType
import com.kappmaker.devicepreview.core.SimulatedDevice
import com.kappmaker.devicepreview.devices.allDevices
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
public fun ConfigurationView(
    modifier: Modifier = Modifier,
    simulatedDevice: SimulatedDevice,
    onChangeConfiguration: (SimulatedDevice) -> Unit
) {
    val configuration = simulatedDevice.configuration
    val device = simulatedDevice.device
    var isAllDevicesShown by remember { mutableStateOf(false) }
    if (isAllDevicesShown) {
        AllDevicesView(
            onClickDevice = {
                onChangeConfiguration(simulatedDevice.copy(device = it))
                isAllDevicesShown = false
            },
            onDismiss = {
                isAllDevicesShown = false
            })
    }

    Column(
        modifier = modifier
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(16.dp),
            text = "Configuration",
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            SelectedDeviceView(
                modifier = Modifier.fillMaxWidth(),
                device = device
            ) {
                isAllDevicesShown = true
            }

            OrientationView(
                modifier = Modifier.fillMaxWidth(),
                isPortrait = configuration.isPortrait
            ) {
                onChangeConfiguration(
                    simulatedDevice.copy(
                        configuration = configuration.copy(
                            isPortrait = configuration.isPortrait.not()
                        )
                    )
                )
            }

            DarkLightModeView(
                modifier = Modifier.fillMaxWidth(),
                isLightMode = configuration.isDarkMode.not()
            ) {
                onChangeConfiguration(
                    simulatedDevice.copy(
                        configuration = configuration.copy(
                            isDarkMode = configuration.isDarkMode.not()
                        )
                    )
                )
            }

            TestPreviewMode(
                modifier = Modifier.fillMaxWidth(),
                isPreviewModeEnabled = configuration.isPreviewModeEnabled
            ) {
                onChangeConfiguration(
                    simulatedDevice.copy(
                        configuration = configuration.copy(
                            isPreviewModeEnabled = configuration.isPreviewModeEnabled.not()
                        )
                    )
                )
            }


        }
    }
}

@Composable
private fun OrientationView(
    modifier: Modifier = Modifier,
    isPortrait: Boolean = true,
    onChangeOrientation: () -> Unit
) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "Orientation",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(if (isPortrait) "Portrait" else "Landscape", color = Color.White)
        }


        Spacer(modifier = Modifier.weight(1f).defaultMinSize(minWidth = 16.dp))
        IconButton(onClick = { onChangeOrientation() }) {
            val animatedRotation by animateFloatAsState(
                targetValue = if (isPortrait) 45f else 135f,
                label = "rotation"
            )
            //Animate rotation
            Icon(
                tint = Color.White,
                modifier = Modifier.rotate(animatedRotation),
                imageVector = Icons.Default.ScreenRotation,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun DarkLightModeView(
    modifier: Modifier = Modifier,
    isLightMode: Boolean = true,
    onChangeMode: () -> Unit
) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "Theme",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(if (isLightMode) "Light" else "Dark", color = Color.White)
        }


        Spacer(modifier = Modifier.weight(1f).defaultMinSize(minWidth = 16.dp))
        IconButton(onClick = { onChangeMode() }) {
            val icon = if (isLightMode) Icons.Default.LightMode else Icons.Default.DarkMode
            //Animate rotation
            Icon(
                tint = Color.White,
                imageVector = icon,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun SelectedDeviceView(
    modifier: Modifier = Modifier,
    device: Device,
    onClickChange: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClickChange() }.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "Device",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(device.name, color = Color.White)
        }


        Spacer(modifier = Modifier.weight(1f).defaultMinSize(minWidth = 16.dp))
        IconButton(onClick = { onClickChange() }) {
            val icon = when (device.type) {
                DeviceType.ANDROID -> Icons.Filled.PhoneAndroid
                DeviceType.IOS -> Icons.Filled.PhoneIphone
                DeviceType.OTHER -> Icons.Filled.DeviceHub
            }
            //Animate rotation
            Icon(
                tint = Color.White,
                imageVector = icon,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun TestPreviewMode(
    modifier: Modifier = Modifier,
    isPreviewModeEnabled: Boolean = false,
    onClickChange: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClickChange() }.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "Preview Mode",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(if (isPreviewModeEnabled) "Enabled" else "Disabled", color = Color.White)
        }


        Spacer(modifier = Modifier.weight(1f).defaultMinSize(minWidth = 16.dp))
        Switch(
            checked = isPreviewModeEnabled,
            onCheckedChange = { onClickChange() },
            colors = SwitchDefaults.colors(
                checkedIconColor = Color.White,
                checkedThumbColor = Color.White
            )
        )
    }
}

@Composable
private fun AllDevicesView(
    modifier: Modifier = Modifier,
    onClickDevice: (Device) -> Unit,
    onDismiss: () -> Unit
) {

    Dialog(onDismissRequest = { onDismiss() }) {
        LazyColumn(modifier = modifier.background(Color.DarkGray)) {
            val groupedDevices = allDevices.groupBy { it.type }
            val sortedDeviceTypes = groupedDevices.keys.sortedBy { it.order }
            sortedDeviceTypes.forEach { deviceType ->
                val devices = groupedDevices[deviceType] ?: emptyList()
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        text = deviceType.displayName,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                item {
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
                items(devices.size) {
                    val device = devices[it]
                    Text(
                        modifier = Modifier.fillMaxWidth().clickable {
                            onClickDevice(device)
                        }.padding(16.dp),
                        text = device.name,
                        color = Color.White,
                    )

                }
            }

        }
    }


}