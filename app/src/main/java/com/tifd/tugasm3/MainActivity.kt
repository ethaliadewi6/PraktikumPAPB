package com.tifd.tugasm3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import com.tifd.tugasm3.ui.theme.TugasM3Theme
import kotlinx.coroutines.coroutineScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TugasM3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreen()
                }
            }
        }
    }
}

@Composable
fun MyScreen() {
    var inputText by remember { mutableStateOf("") }
    var numberText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    val context = LocalContext.current

    val isFormComplete = inputText.isNotBlank() && numberText.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tampilkan hasil input di atas hanya setelah tombol Simpan diklik
        if (resultText.isNotBlank()) {
            Text(
                text = resultText,
                fontSize = 16.sp,
                color = Color(0xFF4682B4),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Input Nama Lengkap
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Icon Profile",
                tint = Color(0xFF4682B4),
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Masukkan nama lengkap", color = Color(0xFF4682B4), fontSize = 14.sp) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color(0xFF4682B4),
                    unfocusedTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    cursorColor = Color(0xFF4682B4)
                ),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input NIM
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Icon NIM",
                tint = Color(0xFF4682B4),
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
            TextField(
                value = numberText,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        numberText = it
                    }
                },
                label = { Text("Masukkan NIM", color = Color(0xFF4682B4), fontSize = 14.sp) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color(0xFF4682B4),
                    unfocusedTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    cursorColor = Color(0xFF4682B4)
                ),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Set hasil ke resultText
                resultText = "Nama: $inputText\nNIM: $numberText"
                // Tampilkan Toast saat tombol diklik
                Toast.makeText(context, "Nama: $inputText\nNIM: $numberText", Toast.LENGTH_SHORT).show()
            },
            enabled = isFormComplete,
            modifier = Modifier
                .height(50.dp)
                .background(
                    if (isFormComplete) Brush.horizontalGradient(
                        colors = listOf(Color(0xFF87CEEB), Color(0xFF4682B4))
                    ) else Brush.horizontalGradient(
                        colors = listOf(Color(0xFFB0B0B0), Color(0xFF808080))
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Text("Simpan", color = Color.White, fontSize = 18.sp)
        }

        // Add long press handling (opsional)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            if (isFormComplete) {
                                Toast.makeText(
                                    context,
                                    "Nama: $inputText\nNIM: $numberText",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TugasM3Theme {
        MyScreen()
    }
}

