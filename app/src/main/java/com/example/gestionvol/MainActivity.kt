package com.example.gestionvol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import com.example.gestionvol.data.database.FlightsDatabase
import com.example.gestionvol.ui.screens.FlightSearchScreen
import com.example.gestionvol.ui.theme.GestionVolTheme
import com.example.gestionvol.ui.viewmodel.FlightSearchViewModel
import com.example.gestionvol.utils.SearchPreferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = FlightsDatabase.getDatabase(this)

        val airportDao = database.airportDao()
        val favoriteDao = database.favoriteDao()

        val searchPreferences = SearchPreferences(this)
        val factory = FlightSearchViewModel.Factory(airportDao, favoriteDao, searchPreferences)
        val viewModel = ViewModelProvider(this, factory)[FlightSearchViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            GestionVolTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlightSearchScreen(viewModel = viewModel)
                }
            }
        }
    }
}