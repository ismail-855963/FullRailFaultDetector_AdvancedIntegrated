package com.example.railfalldetector

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.railfalldetector.databinding.FragmentCalibrationBinding

class CalibrationFragment : Fragment(), SensorEventListener {
    private var _binding: FragmentCalibrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var sensorManager: SensorManager
    private var accelSensor: Sensor? = null

    private var refAx = 0f
    private var refAy = 0f
    private var refAz = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalibrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorManager = requireContext().getSystemService(SensorManager::class.java)
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        binding.btnStartCalibration.setOnClickListener {
            binding.tvCalibStatus.text = "Kalibrasyon yapılıyor..."
            accelSensor?.also { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
            binding.root.postDelayed({ stopCalibration() }, 3000)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        refAx += event.values[0]
        refAy += event.values[1]
        refAz += event.values[2]
    }

    private fun stopCalibration() {
        sensorManager.unregisterListener(this)
        refAx /= 3f
        refAy /= 3f
        refAz /= 3f
        binding.tvCalibStatus.text = "Referans: [x=%.2f, y=%.2f, z=%.2f]".format(refAx, refAy, refAz)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
