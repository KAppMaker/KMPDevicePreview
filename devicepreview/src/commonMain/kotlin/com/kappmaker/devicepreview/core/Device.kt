package com.kappmaker.devicepreview.core


public interface Device {
    public val name: String             // Device name (e.g., "Pixel 6")
    public val screenSize: ScreenSize   // Screen dimensions
    public val type: DeviceType
}