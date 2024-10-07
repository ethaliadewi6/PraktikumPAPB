package com.tifd.tugasm3.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DatabaseReference
import com.tifd.tugasm3.model.JadwalKuliah

class MatkulScreen(private val database: DatabaseReference) {

    @Composable
    fun DisplayJadwalKuliah() {
        val jadwalKuliahList = remember { mutableStateListOf<JadwalKuliah>() }

        // Fetch data from Firebase Realtime Database
        LaunchedEffect(Unit) {
            database.child("jadwal_kuliah").get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    for (child in snapshot.children) {
                        val jadwal = child.getValue(JadwalKuliah::class.java)
                        if (jadwal != null) {
                            jadwalKuliahList.add(jadwal)
                        }
                    }
                } else {
                    Log.d(TAG, "No data found")
                }
            }.addOnFailureListener { exception ->
                Log.e(TAG, "Error getting data", exception)
            }
        }

        // Tampilkan jadwal kuliah
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(jadwalKuliahList) { _, jadwal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color(0xFF001F3F)
                            )
                            .padding(16.dp)
                    ) {
                        Text(text = "Hari: ${jadwal.hari}", color = Color.White)
                        Text(text = "Jam: ${jadwal.jam}", color = Color.White)
                        Text(text = "Kelas: ${jadwal.kelas}", color = Color.White)
                        Text(text = "Kode: ${jadwal.kode}", color = Color.White)
                        Text(text = "Mata Kuliah: ${jadwal.matakuliah}", color = Color.White)
                        Text(text = "Tahun Kurikulum: ${jadwal.tahunkurikulum}", color = Color.White)
                        Text(text = "Dosen: ${jadwal.dosen}", color = Color.White)
                        Text(text = "Ruang: ${jadwal.ruang}", color = Color.White)
                        Text(text = "Jenis: ${jadwal.jenis}", color = Color.White)
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MatkulScreen"
    }
}
