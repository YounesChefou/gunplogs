package com.example.gunplogs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.gunplogs.data.GunplogAppDatabase
import com.example.gunplogs.data.LocalKitsRepository
import com.example.gunplogs.viewmodels.GunplogViewModel
import com.example.gunplogs.ui.theme.GunplogsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = GunplogViewModel(LocalKitsRepository(GunplogAppDatabase.getDatabase(this.applicationContext).kitDao()))

        enableEdgeToEdge()
        setContent {
            GunplogsTheme {
                GunplogsApp(viewModel = viewModel)
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