package com.example.railfalldetector

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.railfalldetector.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val freqOptions = listOf("1", "3", "5")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, freqOptions)
        binding.spinnerFrequency.adapter = adapter

        val prefs = requireContext().getSharedPreferences("DAT_PREFS", Context.MODE_PRIVATE)
        val savedFreq = prefs.getInt("record_interval", 1).toString()
        binding.spinnerFrequency.setSelection(freqOptions.indexOf(savedFreq))

        binding.btnSaveSettings.setOnClickListener {
            val selected = binding.spinnerFrequency.selectedItem.toString().toInt()
            prefs.edit().putInt("record_interval", selected).apply()
            Toast.makeText(requireContext(), "Kayıt sıklığı kaydedildi: $selected saniye", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
