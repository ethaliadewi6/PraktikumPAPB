package com.tifd.tugasm3.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Define ProfileScreen as a class
class ProfileScreen {

    // Composable function inside the class
    @Composable
    fun Display() {
        var user by remember { mutableStateOf<GitHubUser?>(null) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        // Call GitHub API to get user data
        LaunchedEffect(Unit) {
            val call = RetrofitInstance.api.getUser("ethaliadewi6") // Replace with the desired GitHub username
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

        // Box with a gradient background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF001F3F), Color(0xFF007BFF)) // Gradient colors
                    )
                )
                .padding(16.dp), // Padding for the entire content
            contentAlignment = Alignment.TopCenter // Align content at the top
        ) {
            // UI display
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // If user data is available
                if (user != null) {
                    // Profile picture with a circular shape and border
                    Image(
                        painter = rememberAsyncImagePainter(user!!.avatar_url),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape) // Border around profile picture
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // User information card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp)) // White border with rounded corners
                            .background(Color.Transparent), // Transparent background
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp // Remove shadow for a transparent look
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Username and name
                            Text(text = "@${user!!.login}", style = MaterialTheme.typography.h6, color = Color.White)
                            Text(text = user!!.name ?: "N/A", style = MaterialTheme.typography.body1, color = Color.White)

                            // Followers and following counts
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
                    // If no user data, show loading or error message
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
}
