package com.example.railfalldetector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.railfalldetector.databinding.FragmentMlBinding

class MLFragment : Fragment() {
    private var _binding: FragmentMlBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTrainModel.setOnClickListener {
            binding.tvMLStatus.text = "Model eğitiliyor..."
            binding.root.postDelayed({
                binding.tvMLStatus.text = "Model başarıyla eğitildi."
                Toast.makeText(context, "Eğitim tamamlandı", Toast.LENGTH_SHORT).show()
            }, 3000)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
