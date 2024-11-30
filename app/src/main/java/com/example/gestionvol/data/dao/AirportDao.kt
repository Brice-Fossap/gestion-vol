package com.example.gestionvol.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.gestionvol.data.model.AirportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("""
        SELECT * FROM airport 
        WHERE LOWER(iata_code) LIKE '%' || LOWER(:searchQuery) || '%' 
        OR LOWER(name) LIKE '%' || LOWER(:searchQuery) || '%'
        ORDER BY passengers DESC
    """)
    fun searchAirports(searchQuery: String): Flow<List<AirportEntity>>

    @Query("SELECT * FROM airport WHERE iata_code = :code LIMIT 1")
    suspend fun getAirportByCode(code: String): AirportEntity?

    @Query("""
        SELECT * FROM airport 
        WHERE iata_code != :departureCode 
        ORDER BY passengers DESC
    """)
    fun getPossibleDestinations(departureCode: String): Flow<List<AirportEntity>>
}