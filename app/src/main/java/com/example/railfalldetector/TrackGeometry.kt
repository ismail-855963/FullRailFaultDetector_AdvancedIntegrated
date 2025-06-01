package com.example.railfalldetector

data class TrackCurve(
    val startKm: Double,   // örnek: 425000
    val endKm: Double,     // örnek: 425800
    val superelevation: Int,  // dever değeri mm
    val direction: String     // "Sağ" veya "Sol"
)

object CurveManager {
    val curves = listOf(
        TrackCurve(425000.0, 425800.0, 100, "Sağ"),
        TrackCurve(426200.0, 426700.0, 90, "Sol")
    )

    fun isInCurve(currentMeter: Double): TrackCurve? {
        return curves.find { currentMeter in it.startKm..it.endKm }
    }
}
