package com.kappmaker.devicepreview.core


public data class ScreenSize(
    val width: Float,        // Width in  pixels
    val height: Float,       // Height in  pixels
    val density: Float
) {

    public fun rotated(): ScreenSize {
        return this.copy(width = height, height = width)
    }
}
