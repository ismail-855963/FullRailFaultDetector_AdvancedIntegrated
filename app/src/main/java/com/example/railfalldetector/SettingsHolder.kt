package com.example.railfalldetector

object SettingsHolder {
    var selectedLine: String = ""
    var selectedVehicle: String = ""
    var startKm: Int = 0
    var directionIncreasing: Boolean = true
    var calibrationThreshold: Float = 4.0f // mm cinsinden varsayılan eşik değeri
}
