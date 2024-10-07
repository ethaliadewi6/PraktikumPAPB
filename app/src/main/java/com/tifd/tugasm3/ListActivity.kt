package com.tifd.tugasm3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tifd.tugasm3.navigation.NavigationItem
import com.tifd.tugasm3.screen.MatkulScreen
import com.tifd.tugasm3.screen.ProfileScreen
import com.tifd.tugasm3.screen.TugasScreen
import com.tifd.tugasm3.ui.theme.TugasM3Theme

class ListActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Database
        database = Firebase.database("https://praktikum-papb-f676c-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        setContent {
            TugasM3Theme {
                val navController = rememberNavController() // Navigation Controller
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        // Pastikan NavigationGraph dipanggil di dalam Box dan menggunakan padding dari Scaffold
                        NavigationGraph(navController = navController, database = database)
                    }
                }
            }
        }
    }

    // Bottom Navigation Bar
    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        val items = listOf(
            NavigationItem.JadwalKuliah,
            NavigationItem.Profile,
            NavigationItem.TambahJadwal
        )

        BottomNavigation(
            backgroundColor = Color(0xFF001F3F) // Biru dongker sebagai background BottomNavigation
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(item.icon, contentDescription = item.title) },
                    label = { Text(item.title) },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.popBackStack(navController.graph.startDestinationId, false)
                        }
                    },
                    selectedContentColor = Color.White,  // Warna ikon dan teks saat dipilih
                    unselectedContentColor = Color.LightGray,  // Warna ikon dan teks saat tidak dipilih
                    alwaysShowLabel = true // Selalu menampilkan label
                )
            }
        }
    }

    // Navigation Graph
    @Composable
    fun NavigationGraph(navController: NavHostController, database: DatabaseReference) {
        NavHost(
            navController = navController,
            startDestination = NavigationItem.JadwalKuliah.route
        ) {
            composable(NavigationItem.JadwalKuliah.route) {
                val matkulScreen = MatkulScreen(database)
                matkulScreen.DisplayJadwalKuliah()
            }
            composable(NavigationItem.Profile.route) {
                ProfileScreen().Display()
            }
            composable(NavigationItem.TambahJadwal.route) {
                TugasScreen().Display()  // Panggil Display dari TugasScreen
            }
        }
    }
}
