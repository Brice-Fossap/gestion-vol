package com.example.gestionvol.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestionvol.data.dao.AirportDao
import com.example.gestionvol.data.model.AirportEntity
import com.example.gestionvol.data.model.FavoriteEntity

@Composable
fun FavoriteRouteItem(
    favorite: FavoriteEntity,
    airportDao: AirportDao,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    var departureAirport by remember { mutableStateOf<AirportEntity?>(null) }
    var destinationAirport by remember { mutableStateOf<AirportEntity?>(null) }

    LaunchedEffect(favorite) {
        departureAirport = airportDao.getAirportByCode(favorite.departureCode)
        destinationAirport = airportDao.getAirportByCode(favorite.destinationCode)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                RouteInfo(
                    departureAirport = departureAirport,
                    destinationAirport = destinationAirport
                )
            }
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Supprimer des favoris"
                )
            }
        }
    }
}
