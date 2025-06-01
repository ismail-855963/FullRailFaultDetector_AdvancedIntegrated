package com.example.railfalldetector

data class FaultData(
    val type: String,         // "Düşük", "Sağa Dresaj", vs.
    val startKm: Double,      // 30945.75 metre gibi
    val endKm: Double,
    val lat: Double,
    val lng: Double
) {
    val length: Double
        get() = endKm - startKm

    fun formatKm(meters: Double): String {
        val km = meters.toInt() / 1000
        val m = meters % 1000
        return "%d+%.2f".format(km, m)
    }

    val kmFormatted: String
        get() = "Başlangıç: ${formatKm(startKm)}, Bitiş: ${formatKm(endKm)}, Uzunluk: %.2f m".format(length)
}

object FaultStorage {
    val detectedFaults = mutableListOf<FaultData>()
}
