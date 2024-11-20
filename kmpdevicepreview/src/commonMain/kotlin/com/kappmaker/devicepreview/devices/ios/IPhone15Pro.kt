package com.kappmaker.devicepreview.devices.ios


import com.kappmaker.devicepreview.core.Device
import com.kappmaker.devicepreview.core.DeviceType
import com.kappmaker.devicepreview.core.ScreenSize

public class IPhone15Pro : Device {
    override val name: String
        get() = "iPhone 15 Pro"

    override val screenSize: ScreenSize
        get() = ScreenSize(
            width = 1179f, // Logical pixels width
            height = 2556f, // Logical pixels height
            density = 3f // Device Pixel Ratio (DPR), 3x scale for iOS
        )

    override val type: DeviceType
        get() = DeviceType.IOS
}
