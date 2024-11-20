package com.kappmaker.devicepreview.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kappmaker.devicepreview.core.SimulatedDevice
import com.kappmaker.devicepreview.devices.android.Pixel6
import com.kappmaker.devicepreview.getSourceSetName


@Composable
public fun DeviceWithConfigurationViewExpanded(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var simulatedDevice by remember { mutableStateOf(SimulatedDevice(device = Pixel6())) }
    if (simulatedDevice.configuration.isPreviewModeEnabled.not()) {
        content()
        return
    }
    Row(modifier = modifier) {
        val contentModifier = Modifier
            .weight(1f)
            .fillMaxHeight()

        SimulatedDevicePreview(
            simulatedDevice = simulatedDevice,
            modifier = contentModifier,
            content = content
        )
        ConfigurationView(
            modifier = Modifier.fillMaxHeight().width(IntrinsicSize.Max),
            simulatedDevice = simulatedDevice,
            onChangeConfiguration = {
                simulatedDevice = it
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun DeviceWithConfigurationViewCompact(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var simulatedDevice by remember { mutableStateOf(SimulatedDevice(device = Pixel6())) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            dragHandle = {},
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            // Sheet content
            ConfigurationView(
                modifier = Modifier.fillMaxSize(),
                simulatedDevice = simulatedDevice,
                onChangeConfiguration = { newSimulatedDevice ->
                    simulatedDevice = newSimulatedDevice
                })
        }
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (simulatedDevice.configuration.isPreviewModeEnabled.not()) {
            content()
            return
        }
        val contentModifier = Modifier.weight(1f)
        SimulatedDevicePreview(
            modifier = contentModifier,
            simulatedDevice = simulatedDevice,
            content = content
        )

        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            onClick = { showBottomSheet = true })
        {
            Text("Show Configuration")
        }

    }


}

@Composable
public fun DeviceWithConfigurationView(
    modifier: Modifier = Modifier,
    size: WindowSizeClass,
    content: @Composable () -> Unit
) {
    when (size.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> DeviceWithConfigurationViewExpanded(
            modifier = modifier,
            content = content
        )

        else -> DeviceWithConfigurationViewCompact(
            modifier = modifier,
            content = content
        )
    }
}

@Composable
public fun DeviceWithConfigurationView(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    when (getSourceSetName()) {
        "androidMain", "iosMain" -> DeviceWithConfigurationViewCompact(
            modifier = modifier,
            content = content
        )

        else -> DeviceWithConfigurationViewExpanded(
            modifier = modifier,
            content = content
        )
    }
}