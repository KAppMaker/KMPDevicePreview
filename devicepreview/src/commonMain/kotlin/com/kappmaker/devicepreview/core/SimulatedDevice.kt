package com.kappmaker.devicepreview.core

public data class SimulatedDevice(
    val device: Device,
    val configuration: DeviceConfiguration = DeviceConfiguration()
){

    val currentScreenSize: ScreenSize
        get() = if (configuration.isPortrait) {
            device.screenSize
        } else {
            device.screenSize.rotated()
        }
}
