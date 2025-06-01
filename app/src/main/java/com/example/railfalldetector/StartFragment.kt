package com.example.railfalldetector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.railfalldetector.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val hatlar = listOf("İstanbul - Ankara", "Ankara - Konya", "Eskişehir - İstanbul")
        val araclar = listOf("YHT80000", "HT65000", "EMU TCDD")

        binding.spinnerHat.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, hatlar)
        binding.spinnerArac.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, araclar)

        binding.btnBasla.setOnClickListener {
            val kmStr = binding.etKmStart.text.toString()
            if (kmStr.isEmpty()) {
                Toast.makeText(context, "Lütfen kilometre bilgisini girin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            SettingsHolder.selectedLine = binding.spinnerHat.selectedItem.toString()
            SettingsHolder.selectedVehicle = binding.spinnerArac.selectedItem.toString()
            SettingsHolder.startKm = kmStr.toInt()
            SettingsHolder.directionIncreasing = binding.switchDirection.isChecked

            findNavController().navigate(R.id.action_start_to_calibration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

object SettingsHolder {
    var selectedLine: String = ""
    var selectedVehicle: String = ""
    var startKm: Int = 0
    var directionIncreasing: Boolean = true
}
