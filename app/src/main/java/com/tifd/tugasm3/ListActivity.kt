package com.tifd.tugasm3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tifd.tugasm3.ui.theme.TugasM3Theme

class ListActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the database with the specific URL
        database = Firebase.database("https://praktikum-papb-f676c-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        setContent {
            TugasM3Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DisplayJadwalKuliah()
                }
            }
        }
    }

    @Composable
    fun DisplayJadwalKuliah() {
        val jadwalKuliahList = remember { mutableStateListOf<JadwalKuliah>() }
        val context = LocalContext.current

        // Ambil data dari Realtime Database
        LaunchedEffect(Unit) {
            database.child("jadwal_kuliah").get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    // Mengambil data dari snapshot
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

        // Tampilkan data dengan LazyColumn untuk memungkinkan scrolling
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(jadwalKuliahList) { index, jadwal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(Color(0xFF001F3F), Color(0xFF007BFF))
                                )
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
        private const val TAG = "ListActivity"
    }
}

data class JadwalKuliah(
    val hari: String = "",
    val jam: String = "",
    val kelas: String = "",
    val kode: String = "",
    val matakuliah: String = "",
    val tahunkurikulum: String = "",
    val dosen: String = "",
    val ruang: String = "",
    val jenis: String = ""
)
