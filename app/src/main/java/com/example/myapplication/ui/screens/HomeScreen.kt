package com.example.myapplication.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myapplication.viewmodel.AppViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: AppViewModel
) {
    val nasaUrl by viewModel.nasaImageUrl.collectAsState()
    val nasaTitle by viewModel.nasaTitle.collectAsState()
    val nasaExplanation by viewModel.nasaExplanation.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadNasaImage("Cbh1wl3fRp6r7BXEjAhVkj3t7hK9JVsm83HjFMTd")
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Trista's App",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 12.dp, bottom = 20.dp)
            )

            Button(
                onClick = { navController.navigate("quote") },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Daily Quote")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { navController.navigate("tortoiseshell") },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Thank You")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { navController.navigate("messages") },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Click Here")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(220.dp)
            ) {
                AnimatedContent(
                    targetState = nasaUrl,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    }
                ) { url ->
                    if (url.isNotEmpty()) {
                        AsyncImage(
                            model = url,
                            contentDescription = "NASA Astronomy Picture of the Day",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.loadNasaImage("Cbh1wl3fRp6r7BXEjAhVkj3t7hK9JVsm83HjFMTd")
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Load NASA Image")
            }

            Spacer(modifier = Modifier.height(16.dp))



            if (nasaExplanation.isNotEmpty()) {

                var expanded by remember { mutableStateOf(false) }

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)
                    ),
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(0.9f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {

                        if (nasaTitle.isNotEmpty()) {
                            Text(
                                text = nasaTitle,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                        }

                        val previewText = if (nasaExplanation.length > 150)
                            nasaExplanation.take(150) + "..."
                        else
                            nasaExplanation

                        Text(
                            text = if (expanded) nasaExplanation else previewText,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = { expanded = !expanded }) {
                            Text(if (expanded) "Read Less" else "Read More")
                        }
                    }
                }
            }
        }
    }
}
