package com.tifd.tugasm3.navigation

sealed class Screen (val route: String) {
    object JadwalKuliah : Screen("jadwalKuliahScreen")
    object Profile : Screen("profileScreen")
    object TambahJadwal : Screen("tambahJadwalScreen")
}