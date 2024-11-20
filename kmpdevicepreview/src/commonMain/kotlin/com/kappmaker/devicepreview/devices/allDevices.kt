package com.kappmaker.devicepreview.devices

import com.kappmaker.devicepreview.core.Device
import com.kappmaker.devicepreview.devices.android.Pixel6
import com.kappmaker.devicepreview.devices.ios.IPhone15Pro
import com.kappmaker.devicepreview.devices.other.GenericDevice

internal val allDevices: List<Device> = listOf(
    Pixel6(),
    GenericDevice(),
    IPhone15Pro(),
)