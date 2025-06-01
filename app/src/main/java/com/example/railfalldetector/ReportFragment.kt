package com.example.railfalldetector

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.railfalldetector.databinding.FragmentReportBinding
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class ReportFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
            FaultStorage.detectedFaults.map {
                "${it.type} - ${it.kmFormatted}"
            })
        binding.listFaults.adapter = adapter

        binding.btnExportCSV.setOnClickListener {
            if (checkPermission()) exportCSV()
        }

        binding.btnExportPDF.setOnClickListener {
            if (checkPermission()) exportPDF()
        }
    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2000)
            return false
        }
        return true
    }

    private fun exportCSV() {
        val filename = "rail_report_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.csv"
        val file = File(requireContext().getExternalFilesDir(null), filename)
        val writer = FileWriter(file)
        writer.append("Tip,Başlangıç,Bitiş,Uzunluk(m),Lat,Lon\n")
        FaultStorage.detectedFaults.forEach {
            writer.append("${it.type},${it.formatKm(it.startKm)},${it.formatKm(it.endKm)},${"%.2f".format(it.length)},${it.lat},${it.lng}\n")
        }
        writer.flush()
        writer.close()
        Toast.makeText(context, "CSV oluşturuldu: ${file.absolutePath}", Toast.LENGTH_LONG).show()
    }

    private fun exportPDF() {
        val pdfDocument = PdfDocument()
        val paint = Paint()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        paint.textSize = 12f
        var y = 25
        canvas.drawText("Arıza Raporu - ${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())}", 20f, y.toFloat(), paint)
        y += 30

        FaultStorage.detectedFaults.forEach {
            val line = "${it.type} | ${it.formatKm(it.startKm)} - ${it.formatKm(it.endKm)} | ${"%.2f".format(it.length)} m"
            canvas.drawText(line, 20f, y.toFloat(), paint)
            y += 20
            if (y > 800) {
                pdfDocument.finishPage(page)
                return@forEach
            }
        }

        pdfDocument.finishPage(page)

        val filename = "rail_report_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.pdf"
        val file = File(requireContext().getExternalFilesDir(null), filename)
        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()

        Toast.makeText(context, "PDF kaydedildi: ${file.absolutePath}", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
