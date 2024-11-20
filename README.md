# KMPDevicePreview - Kotlin & Compose Multiplatform Preview Library

KMPDevicePreview is a Compose Multiplatform library for previewing and simulating your app. It lets you test your app or any part of it on different screen sizes, devices, and settings like dark mode or portrait/landscape orientation. This library works on Android, iOS, Web (WASM/Kotlin), JS and  Desktop (JVM). You can even use it to create web-based demos to showcase your app online.

## Getting Started
To use **KMPDevicePreview**, add the library to your build.gradle file:
Latest version: [![Maven Central](https://img.shields.io/maven-central/v/com.kappmaker/kmpdevicepreview?color=blue)](https://search.maven.org/search?q=g:com.kappmaker).

```gradle
implementation("com.kappmaker:kmpdevicepreview:<version>")

```
Wrap your root App composable or any view with `DeviceWithConfigurationView` to display a preview:
```kotlin
DeviceWithConfigurationView {
    App()
}
```



https://github.com/user-attachments/assets/6393bd5b-a148-4903-a641-5d83bbc80d1d



## Advanced Configuration

### Custom Device
To preview only SimulatedDevice without any configuration view:
```kotlin

SimulatedDevicePreview(
    simulatedDevice = SimulatedDevice(
        device = Pixel6(), // Use a predefined device or create your own
        configuration = DeviceConfiguration(isDarkMode = true, isPortrait = true) // Set dark mode and orientation
    )
) {
    // Your composable content
    App()
}

```

You can create custom device size by implementing the `Device` interface and override its size properties, then pass it into `SimulatedDevice`.

### Theme Configuration
Use `SimulatedDeviceThemeIsDark.current` for testing light/dark mode previews:

```kotlin
val isDark by SimulatedDeviceThemeIsDark.current
MaterialTheme(colorScheme = if (isDark) darkColorScheme() else lightColorScheme()){
    ...
}


```
####
This library is inspired by [Flutter Device Preview](https://github.com/aloisdeniel/flutter_device_preview). It brings similar functionality to the Compose Multiplatform ecosystem.

