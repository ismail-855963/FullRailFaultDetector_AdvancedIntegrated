package com.example.railfalldetector

data class ManualFault(
    val km: String,
    val description: String,
    val timestamp: Long
)

object ManualFaults {
    val list = mutableListOf<ManualFault>()
}
