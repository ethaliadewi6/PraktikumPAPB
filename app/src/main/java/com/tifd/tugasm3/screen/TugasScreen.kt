package com.tifd.tugasm3.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class TugasScreen {

    @Composable
    fun Display() {
        var tugasTitle by remember { mutableStateOf(TextFieldValue("")) }
        var tugasDescription by remember { mutableStateOf(TextFieldValue("")) }
        var daftarTugas by remember { mutableStateOf(listOf<Pair<String, String>>()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Input fields for task title and description
            OutlinedTextField(
                value = tugasTitle,
                onValueChange = { tugasTitle = it },
                label = { Text("Judul Tugas", color = Color.Black) },  // Label teks berwarna hitam
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,  // Warna bingkai saat aktif
                    unfocusedBorderColor = Color.Black, // Warna bingkai saat tidak aktif
                    textColor = Color.Black,            // Warna teks saat mengetik
                    cursorColor = Color.Black           // Warna kursor hitam
                )
            )

            OutlinedTextField(
                value = tugasDescription,
                onValueChange = { tugasDescription = it },
                label = { Text("Deskripsi Tugas", color = Color.Black) },  // Label teks berwarna hitam
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,  // Warna bingkai saat aktif
                    unfocusedBorderColor = Color.Black, // Warna bingkai saat tidak aktif
                    textColor = Color.Black,            // Warna teks saat mengetik
                    cursorColor = Color.Black           // Warna kursor hitam
                )
            )

            // Button to add task
            Button(
                onClick = {
                    // Add task to the list if title and description are not empty
                    if (tugasTitle.text.isNotBlank() && tugasDescription.text.isNotBlank()) {
                        daftarTugas = daftarTugas + (tugasTitle.text to tugasDescription.text)
                        // Clear the text fields
                        tugasTitle = TextFieldValue("")
                        tugasDescription = TextFieldValue("")
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF001F3F)  // Biru dongker sebagai background
                )
            ) {
                Text("Tambah Tugas", color = Color.White)  // Warna teks putih agar kontras
            }

            // Display the list of tasks
            if (daftarTugas.isNotEmpty()) {
                Text(
                    text = "Daftar Tugas:",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(daftarTugas) { (title, description) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = 4.dp
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Judul: $title", style = MaterialTheme.typography.subtitle1)
                                Text(text = "Deskripsi: $description", style = MaterialTheme.typography.body2)
                            }
                        }
                    }
                }
            }
        }
    }
}
