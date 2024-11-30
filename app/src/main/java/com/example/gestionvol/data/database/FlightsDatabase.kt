package com.example.gestionvol.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gestionvol.data.dao.AirportDao
import com.example.gestionvol.data.dao.FavoriteDao
import com.example.gestionvol.data.model.AirportEntity
import com.example.gestionvol.data.model.FavoriteEntity

@Database(entities = [AirportEntity::class, FavoriteEntity::class], version = 1)
abstract class FlightsDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: FlightsDatabase? = null

        fun getDatabase(context: Context): FlightsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FlightsDatabase::class.java,
                    "flight_search.db"
                )
                    .createFromAsset("flight_search.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}