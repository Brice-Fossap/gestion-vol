package com.example.gestionvol.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestionvol.data.model.AirportEntity

@Composable
fun DestinationsList(
    destinations: List<AirportEntity>,
    departureAirport: AirportEntity,
    onAddToFavorites: (AirportEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(destinations) { destination ->
            DestinationItem(
                departureAirport = departureAirport,
                destinationAirport = destination,
                onAddToFavorites = { onAddToFavorites(destination) }
            )
        }
    }
}