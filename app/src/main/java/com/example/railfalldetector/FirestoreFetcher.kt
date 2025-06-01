package com.example.railfalldetector

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreFetcher {
    private val db = FirebaseFirestore.getInstance()
    private val adminUIDs = listOf("adminUidBuraya") // admin UID buraya

    fun fetchFaults(onComplete: (List<FaultData>) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val query = if (uid in adminUIDs) {
            db.collection("faults")
        } else {
            db.collection("faults").whereEqualTo("userId", uid)
        }

        query.get().addOnSuccessListener { result ->
            val faults = result.map { doc ->
                FaultData(
                    type = doc.getString("type") ?: "",
                    startKm = 0.0,
                    endKm = 0.0,
                    lat = doc.getDouble("lat") ?: 0.0,
                    lng = doc.getDouble("lng") ?: 0.0
                )
            }
            onComplete(faults)
        }
    }
}
