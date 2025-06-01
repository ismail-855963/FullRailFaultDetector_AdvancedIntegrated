package com.example.railfalldetector

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.railfalldetector.databinding.FragmentLiveGraphBinding

class LiveGraphFragment : Fragment() {
    private var _binding: FragmentLiveGraphBinding? = null
    private val binding get() = _binding!!
    private var kmMetre = 0.0
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 1000L // 1 saniye

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLiveGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val kmUpdater = object : Runnable {
        override fun run() {
            val delta = 0.27  // her saniye yaklaşık 1 km/h = 0.27 m/s
            kmMetre += if (SettingsHolder.directionIncreasing) delta else -delta
            val totalMetre = SettingsHolder.startKm + kmMetre
            val inCurve = CurveManager.isInCurve(totalMetre)
            val activeThreshold = if (inCurve != null) SettingsHolder.calibrationThreshold + 2 else SettingsHolder.calibrationThreshold
            binding.tvGraphTitle.text = if (inCurve != null) "Kurp: ${inCurve.direction}, Dever: ${inCurve.superelevation} mm" else "Canlı Titreşim Grafiği"
        
            val kmInt = (totalMetre / 1000).toInt()
            val metre = totalMetre % 1000
            binding.tvCurrentKm.text = "Anlık Km: %d+%.2f".format(kmInt, metre)
            handler.postDelayed(this, updateInterval)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val locationListener = android.location.LocationListener { location ->
            val newRegion = TrackRegionManager.detectRegion(location.latitude, location.longitude)
            if (newRegion != null && newRegion != SettingsHolder.selectedLine) {
                SettingsHolder.selectedLine = newRegion
                android.widget.Toast.makeText(requireContext(), "Yeni hat: $newRegion", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
        val lm = requireContext().getSystemService(android.content.Context.LOCATION_SERVICE) as android.location.LocationManager
        try {
            lm.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 5000L, 5f, locationListener)
        } catch (e: SecurityException) {
            android.widget.Toast.makeText(context, "Konum izni gerekli", android.widget.Toast.LENGTH_SHORT).show()
        }

        binding.btnManualFault.setOnClickListener {
            val kmText = binding.tvCurrentKm.text.toString().replace("Anlık Km: ", "")
            val now = System.currentTimeMillis()
            val desc = "Manuel Arıza (kullanıcı tarafından işaretlendi)"
            ManualFaults.list.add(ManualFault(kmText, desc, now))
            android.widget.Toast.makeText(requireContext(), "Manuel arıza kaydedildi", android.widget.Toast.LENGTH_SHORT).show()
        }

        kmMetre = 0.0
        handler.post(kmUpdater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(kmUpdater)
        _binding = null
    }
}
