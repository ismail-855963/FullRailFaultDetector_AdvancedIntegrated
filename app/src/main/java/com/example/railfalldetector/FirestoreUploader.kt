package com.example.railfalldetector

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreUploader {
    private val db = FirebaseFirestore.getInstance()

    fun uploadFault(fault: FaultData) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val data = hashMapOf(
            "type" to fault.type,
            "startKm" to fault.formatKm(fault.startKm),
            "endKm" to fault.formatKm(fault.endKm),
            "length" to "%.2f".format(fault.length),
            "lat" to fault.lat,
            "lng" to fault.lng,
            "userId" to uid,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("faults")
            .add(data)
            .addOnSuccessListener { Log.d("FirestoreUploader", "Başarıyla yüklendi") }
            .addOnFailureListener { e -> Log.w("FirestoreUploader", "Yükleme hatası", e) }
    }
}
