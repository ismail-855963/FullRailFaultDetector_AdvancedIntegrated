package com.example.railfalldetector

import android.location.Location

data class TrackRegion(
    val name: String,
    val minLat: Double,
    val maxLat: Double,
    val minLng: Double,
    val maxLng: Double
)

object TrackRegionManager {
    val regions = listOf(
        TrackRegion("Hat 1", 39.90, 39.95, 32.80, 32.85),
        TrackRegion("Hat 2", 39.96, 40.01, 32.86, 32.90)
    )

    fun detectRegion(lat: Double, lng: Double): String? {
        return regions.find {
            lat in it.minLat..it.maxLat && lng in it.minLng..it.maxLng
        }?.name
    }
}
