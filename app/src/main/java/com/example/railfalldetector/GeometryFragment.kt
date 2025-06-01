package com.example.railfalldetector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.railfalldetector.databinding.FragmentGeometryBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class GeometryFragment : Fragment() {
    private var _binding: FragmentGeometryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGeometryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dresajData = List(20) { Entry(it.toFloat(), (-3..3).random().toFloat()) }
        val dusukData = List(20) { Entry(it.toFloat(), (0..5).random().toFloat()) }

        val dresajSet = LineDataSet(dresajData, "Drezaj (mm)")
        dresajSet.lineWidth = 2f
        dresajSet.setDrawCircles(false)
        dresajSet.color = android.graphics.Color.BLUE

        val dresajLimit = com.github.mikephil.charting.components.LimitLine(SettingsHolder.calibrationThreshold, "Eşik")
        dresajLimit.lineColor = android.graphics.Color.RED
        dresajLimit.lineWidth = 1.5f
        binding.chartDresaj.axisLeft.addLimitLine(dresajLimit)
        val dusukSet = LineDataSet(dusukData, "Düşük (mm)")
        dusukSet.lineWidth = 2f
        dusukSet.setDrawCircles(false)
        dusukSet.color = android.graphics.Color.MAGENTA

        val dusukLimit = com.github.mikephil.charting.components.LimitLine(SettingsHolder.calibrationThreshold, "Eşik")
        dusukLimit.lineColor = android.graphics.Color.RED
        dusukLimit.lineWidth = 1.5f
        binding.chartDusuk.axisLeft.addLimitLine(dusukLimit)

        binding.chartDresaj.data = LineData(dresajSet)
        binding.chartDusuk.data = LineData(dusukSet)
        binding.chartDresaj.invalidate()
        binding.chartDusuk.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
