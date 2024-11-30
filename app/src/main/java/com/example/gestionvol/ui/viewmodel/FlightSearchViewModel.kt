package com.example.gestionvol.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gestionvol.data.dao.AirportDao
import com.example.gestionvol.data.dao.FavoriteDao
import com.example.gestionvol.data.model.AirportEntity
import com.example.gestionvol.data.model.FavoriteEntity
import com.example.gestionvol.utils.SearchPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class FlightSearchViewModel(
    val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao,
    private val searchPreferences: SearchPreferences
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class)
    val suggestedAirports = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.length >= 2) {
                airportDao.searchAirports(query)
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedAirport = MutableStateFlow<AirportEntity?>(null)
    val selectedAirport = _selectedAirport.asStateFlow()

    val possibleDestinations = selectedAirport.flatMapLatest { airport ->
        airport?.let {
            airportDao.getPossibleDestinations(it.iataCode)
        } ?: flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val favorites = favoriteDao.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            searchPreferences.searchQuery.collect { savedQuery ->
                _searchQuery.value = savedQuery
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            searchPreferences.saveSearchQuery(query)
        }
    }

    fun onAirportSelected(airport: AirportEntity) {
        _selectedAirport.value = airport
        _searchQuery.value = ""
    }

    fun addToFavorites(destinationAirport: AirportEntity) {
        viewModelScope.launch {
            selectedAirport.value?.let { departure ->
                val favorite = FavoriteEntity(
                    departureCode = departure.iataCode,
                    destinationCode = destinationAirport.iataCode
                )
                favoriteDao.insertFavorite(favorite)
            }
        }
    }

    fun removeFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch {
            favoriteDao.deleteFavorite(favorite)
        }
    }

    class Factory(
        private val airportDao: AirportDao,
        private val favoriteDao: FavoriteDao,
        private val searchPreferences: SearchPreferences
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FlightSearchViewModel(airportDao, favoriteDao, searchPreferences) as T
        }
    }
}