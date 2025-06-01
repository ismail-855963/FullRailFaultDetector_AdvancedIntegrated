package com.example.railfalldetector

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.railfalldetector.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        // Örnek eşik üzeri hata verileri
        val faults = listOf(
            FaultEntry("425+100", "Düşük", 6.2f, SettingsHolder.calibrationThreshold, System.currentTimeMillis()),
            FaultEntry("426+200", "Drezaj", 5.1f, SettingsHolder.calibrationThreshold, System.currentTimeMillis())
        )

        for (fault in faults) {
            if (fault.value > fault.threshold) {
                val latLng = LatLng(39.93 + Math.random() * 0.01, 32.85 + Math.random() * 0.01)
                val renk = if (fault.type == "Düşük") BitmapDescriptorFactory.HUE_MAGENTA else BitmapDescriptorFactory.HUE_RED
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title("${fault.km} ${fault.type}")
                        .snippet("${fault.value} mm (Yüksek)")
                        .icon(BitmapDescriptorFactory.defaultMarker(renk))
                )
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(39.93, 32.85), 13f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
