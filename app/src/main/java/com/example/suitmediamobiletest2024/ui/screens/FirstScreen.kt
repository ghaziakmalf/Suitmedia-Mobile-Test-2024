package com.example.suitmediamobiletest2024.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.suitmediamobiletest2024.R
import com.example.suitmediamobiletest2024.ui.theme.SuitmediaMobileTest2024Theme
import com.example.suitmediamobiletest2024.ui.theme.Suitmedia_Green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var sentence by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_photo),
            contentDescription = "Placeholder photo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        @Suppress("DEPRECATION")
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name", style = MaterialTheme.typography.labelLarge) },
            textStyle = MaterialTheme.typography.displayMedium,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        @Suppress("DEPRECATION")
        TextField(
            value = sentence,
            onValueChange = { sentence = it },
            label = { Text("Palindrome", style = MaterialTheme.typography.labelLarge) },
            textStyle = MaterialTheme.typography.displayMedium,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                val isPalindrome = sentence.replace("\\s".toRegex(), "").equals(sentence.replace("\\s".toRegex(), "").reversed(), ignoreCase = true)
                val message = if (isPalindrome) "Palindrome" else "Not Palindrome"

                if (sentence.isEmpty()) {
                    Toast.makeText(context, "Please input a sentence", Toast.LENGTH_SHORT).show()
                    return@Button
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonColors(
                containerColor = Suitmedia_Green,
                contentColor = Color.White,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            Text(
                "Check",
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                // Check if name is empty
                if (name.isEmpty()) {
                    Toast.makeText(context, "Please input your name", Toast.LENGTH_SHORT).show()
                    return@Button
                } else {
                    Toast.makeText(context, "Welcome, $name", Toast.LENGTH_SHORT).show()
                    navController.navigate("second_screen/$name")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonColors(
                containerColor = Suitmedia_Green,
                contentColor = Color.White,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            Text(
                "Next",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FirstScreenPreview() {
    val navController = rememberNavController()
    SuitmediaMobileTest2024Theme {
        FirstScreen(navController)
    }
}
