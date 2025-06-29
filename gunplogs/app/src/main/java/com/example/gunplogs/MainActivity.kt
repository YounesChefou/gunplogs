package com.example.gunplogs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.gunplogs.ui.GunplogViewModel
import com.example.gunplogs.ui.GunplogsMainScreen
import com.example.gunplogs.ui.theme.GunplogsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GunplogsTheme {
                GunplogsApp(viewModel = GunplogViewModel(this.applicationContext))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GunplogsTheme {
        GunplogsApp()
    }
}