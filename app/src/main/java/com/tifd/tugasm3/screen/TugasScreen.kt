package com.tifd.tugasm3.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tifd.tugasm3.local.Tugas
import com.tifd.tugasm3.local.TugasViewModel

class TugasScreen {

    @Composable
    fun Display(tugasViewModel: TugasViewModel = viewModel()) {
        var tugasTitle by remember { mutableStateOf(TextFieldValue("")) }
        var tugasDescription by remember { mutableStateOf(TextFieldValue("")) }
        val daftarTugas: List<Tugas> by tugasViewModel.tugasList.observeAsState(initial = emptyList())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Input fields for course name and task details
            OutlinedTextField(
                value = tugasTitle,
                onValueChange = { tugasTitle = it },
                label = { Text("Nama Mata Kuliah", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    textColor = Color.Black,
                    cursorColor = Color.Black
                )
            )

            OutlinedTextField(
                value = tugasDescription,
                onValueChange = { tugasDescription = it },
                label = { Text("Detail Tugas", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    textColor = Color.Black,
                    cursorColor = Color.Black
                )
            )

            // Button to add task
            Button(
                onClick = {
                    if (tugasTitle.text.isNotBlank() && tugasDescription.text.isNotBlank()) {
                        // Save task to database
                        tugasViewModel.insert(
                            Tugas(
                                matkul = tugasTitle.text,
                                detailTugas = tugasDescription.text,
                                selesai = false
                            )
                        )
                        // Clear the text fields
                        tugasTitle = TextFieldValue("")
                        tugasDescription = TextFieldValue("")
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF001F3F)
                )
            ) {
                Text("Tambah Tugas", color = Color.White)
            }

            // Display the list of tasks with scrollable view
            if (daftarTugas.isNotEmpty()) {
                Text(
                    text = "Daftar Tugas:",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    items(daftarTugas) { tugas ->
                        TugasItem(tugas = tugas, onTugasCompleted = {
                            tugasViewModel.updateTugasStatus(tugas.id, !tugas.selesai)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun TugasItem(tugas: Tugas, onTugasCompleted: (Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Nama Mata Kuliah: ${tugas.matkul}", style = MaterialTheme.typography.subtitle1)
                Text(text = "Detail Tugas: ${tugas.detailTugas}", style = MaterialTheme.typography.body2)
            }

            // Button to mark as completed or not with blue text for completed
            Button(
                onClick = { onTugasCompleted(!tugas.selesai) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent // Button with transparent background
                ),
                elevation = ButtonDefaults.elevation(0.dp) // Remove button elevation
            ) {
                Text(
                    text = if (tugas.selesai) "✔️" else "Selesai",
                    color = if (tugas.selesai) Color(0xFF001F3F) else Color.Black
                )
            }
        }
    }
}
