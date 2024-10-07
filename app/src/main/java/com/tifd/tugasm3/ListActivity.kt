package com.tifd.tugasm3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tifd.tugasm3.ui.theme.TugasM3Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the database with the specific URL
        database = Firebase.database("https://praktikum-papb-f676c-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        setContent {
            val navController = rememberNavController()  // Create the NavController
            TugasM3Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Setup navigation with NavHost
                    NavHost(navController = navController, startDestination = "jadwalKuliahScreen") {
                        composable("jadwalKuliahScreen") { DisplayJadwalKuliah(navController) }  // Jadwal Kuliah screen
                        composable("githubFragment") { GitHubFragment() }  // GitHub Fragment screen
                    }
                }
            }
        }
    }

    @Composable
    fun DisplayJadwalKuliah(navController: NavHostController) {
        val jadwalKuliahList = remember { mutableStateListOf<JadwalKuliah>() }

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

        // Scaffold with TopAppBar and LazyColumn
        Scaffold(
            topBar = {
                // Membungkus TopAppBar dalam Box untuk memberikan background gradasi
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp) // Menyesuaikan dengan ukuran TopAppBar standar
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF001F3F), Color(0xFF007BFF))
                            )
                        ),
                    contentAlignment = Alignment.Center // Mengatur konten agar berada di tengah
                ) {
                    // TopAppBar tanpa background karena kita sudah menggunakan Box dengan background gradasi
                    TopAppBar(
                        title = {
                            Text(text = "Jadwal Kuliah", color = Color.White)
                        },
                        backgroundColor = Color.Transparent, // Menjadikan background transparan agar tidak menutupi gradien
                        elevation = 0.dp, // Menghilangkan bayangan
                        actions = {
                            IconButton(onClick = { navController.navigate("githubFragment") }) {
                                Image(
                                    painter = rememberAsyncImagePainter("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png"),
                                    contentDescription = "GitHub",
                                    modifier = Modifier
                                        .size(36.dp) // Ukuran lingkaran logo
                                        .clip(CircleShape) // Membuat gambar menjadi bulat
                                        .background(Color.White) // Tambahkan background putih agar terlihat rapi
                                )
                            }
                        }
                    )
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
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
    }


    @Composable
    fun GitHubFragment() {
        var user by remember { mutableStateOf<GitHubUser?>(null) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        // Panggil API GitHub untuk mendapatkan data pengguna
        LaunchedEffect(Unit) {
            val call = RetrofitInstance.api.getUser("ethaliadewi6") // Ganti dengan username yang diinginkan
            call.enqueue(object : Callback<GitHubUser> {
                override fun onResponse(call: Call<GitHubUser>, response: Response<GitHubUser>) {
                    if (response.isSuccessful) {
                        user = response.body()
                    } else {
                        errorMessage = "Failed to load user data"
                    }
                }

                override fun onFailure(call: Call<GitHubUser>, t: Throwable) {
                    errorMessage = t.message
                }
            })
        }

        // Box untuk background dengan gradasi
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF001F3F), Color(0xFF007BFF)) // Gradasi warna biru
                    )
                )
                .padding(16.dp), // Padding untuk seluruh konten
            contentAlignment = Alignment.TopCenter // Posisikan konten di atas
        ) {
            // UI Tampilan
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Jika data pengguna ada
                if (user != null) {
                    // Gambar Profil dalam Bentuk Lingkaran dengan Border
                    Image(
                        painter = rememberAsyncImagePainter(user!!.avatar_url),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape) // Border untuk gambar profil
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Informasi Pengguna dalam Kartu
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp)) // Border putih dengan bentuk sudut rounded
                            .background(Color.Transparent), // Background transparan
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp // Hilangkan bayangan agar lebih sesuai dengan tampilan transparan
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Username dan Nama
                            Text(text = "@${user!!.login}", style = MaterialTheme.typography.h6, color = Color.White)
                            Text(text = user!!.name ?: "N/A", style = MaterialTheme.typography.body1, color = Color.White)

                            // Followers dan Following dalam Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Followers: ${user!!.followers}", style = MaterialTheme.typography.body2, color = Color.White)
                                Text(text = "Following: ${user!!.following}", style = MaterialTheme.typography.body2, color = Color.White)
                            }
                        }
                    }

                } else {
                    // Jika data belum ada, tampilkan loading atau error
                    if (errorMessage != null) {
                        Text(text = errorMessage ?: "Unknown error", color = Color.Red)
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
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
