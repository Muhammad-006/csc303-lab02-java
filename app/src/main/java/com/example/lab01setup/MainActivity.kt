package com.example.lab01setup

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab01setup.ui.theme.Lab01SetupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Task 8: tagged diagnostic message. Filter Logcat with  tag:CSC303-Lab01
        Log.d("CSC303-Lab01", "MainActivity onCreate called")
        enableEdgeToEdge()
        setContent {
            Lab01SetupTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Task 7: personalised starter screen
                    Greeting(
                        name = "Muhammad Bilal",
                        reg = "FA23-BCE-062",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, reg: String, modifier: Modifier = Modifier) {
    Text(
        text = "CSC303 Lab 01 - Student:\n$name - $reg",
        modifier = modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab01SetupTheme {
        Greeting(name = "Muhammad Bilal", reg = "FA23-BCE-062")
    }
}
