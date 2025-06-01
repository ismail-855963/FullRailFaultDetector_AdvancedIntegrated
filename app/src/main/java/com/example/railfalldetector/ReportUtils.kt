package com.example.railfalldetector

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class FaultEntry(
    val km: String,
    val type: String,
    val value: Float,
    val threshold: Float,
    val timestamp: Long
)

object ReportUtils {

    private fun formatTime(ms: Long): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(ms))
    }

    private fun formatDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun exportCSV(context: Context, entries: List<FaultEntry>): File {
        val file = File(context.getExternalFilesDir(null), "Rapor_${formatDate()}.csv")
        val writer = FileOutputStream(file)
        writer.write("Kilometre;Tür;Değer (mm);Durum;Saat\n".toByteArray())

        for (entry in entries) {
            val durum = if (entry.value > entry.threshold) "Yüksek ${entry.type}" else "Normal"
            val line = "${entry.km};${entry.type};${entry.value};$durum;${formatTime(entry.timestamp)}\n"
            writer.write(line.toByteArray())
        }
        writer.close()
        return file
    }

    fun exportPDF(context: Context, entries: List<FaultEntry>): File {
        val file = File(context.getExternalFilesDir(null), "Rapor_${formatDate()}.txt") // placeholder for PDF
        val writer = FileOutputStream(file)
        writer.write("Rapor Tarihi: ${formatDate()}\n".toByteArray())
        writer.write("Kilometre | Tür | Değer (mm) | Durum | Saat\n".toByteArray())
        writer.write("-----------------------------------------------------\n".toByteArray())

        for (entry in entries) {
            val durum = if (entry.value > entry.threshold) "Yüksek ${entry.type}" else "Normal"
            val line = "${entry.km} | ${entry.type} | ${entry.value} | $durum | ${formatTime(entry.timestamp)}\n"
            writer.write(line.toByteArray())
        }
        writer.close()
        return file
    }
}
