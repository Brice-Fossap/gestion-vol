package com.example.gestionvol.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestionvol.data.dao.AirportDao
import com.example.gestionvol.data.model.FavoriteEntity

@Composable
fun FavoritesList(
    favorites: List<FavoriteEntity>,
    airportDao: AirportDao,
    onRemove: (FavoriteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(favorites) { favorite ->
            FavoriteRouteItem(
                favorite = favorite,
                airportDao = airportDao,
                onRemove = { onRemove(favorite) }
            )
        }
    }
}

