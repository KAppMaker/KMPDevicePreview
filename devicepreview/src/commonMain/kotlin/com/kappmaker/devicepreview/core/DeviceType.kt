package com.kappmaker.devicepreview.core

public enum class DeviceType(public val displayName: String, internal val order: Int) {
    ANDROID("Android", 0),
    IOS("iOS", 1),
    OTHER("Other", 2);


    /**
     * Returns a human-readable name for the device type.
     */
    override fun toString(): String {
        return displayName
    }
}