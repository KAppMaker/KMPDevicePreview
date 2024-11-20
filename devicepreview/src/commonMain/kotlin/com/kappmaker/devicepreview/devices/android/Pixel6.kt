package com.kappmaker.devicepreview.devices.android

import com.kappmaker.devicepreview.core.Device
import com.kappmaker.devicepreview.core.DeviceType
import com.kappmaker.devicepreview.core.ScreenSize

public class Pixel6 : Device {
    override val name: String
        get() = "Pixel 6"


    override val screenSize: ScreenSize
        get() = ScreenSize(
            width = 1080f,
            height = 2400f,
            density = 420 / 160f
        )

    override val type: DeviceType
        get() = DeviceType.ANDROID

}