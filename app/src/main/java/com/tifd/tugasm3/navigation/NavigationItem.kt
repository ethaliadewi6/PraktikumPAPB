package com.tifd.tugasm3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val title: String, val icon: ImageVector) {
    object JadwalKuliah : NavigationItem("jadwalKuliahScreen", "Matkul", Icons.AutoMirrored.Filled.List)
    object Profile : NavigationItem("profileScreen", "Profil", Icons.Default.Person)
    object TambahJadwal : NavigationItem("tambahJadwalScreen", "Tugas", Icons.Default.Add)
}
