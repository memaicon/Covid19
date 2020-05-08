package br.com.tecdev.covid19.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.tecdev.covid19.BuildConfig
import br.com.tecdev.covid19.data.database.dao.CountryDao
import br.com.tecdev.covid19.data.database.dao.TotalCasesDao
import br.com.tecdev.covid19.model.*

@Database(
    entities = [CountryResponse::class, CountryInfo::class, TotalResponse::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(value = [CountryInfoConverter::class])
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = BuildConfig.APPLICATION_ID
    }

    abstract fun countryDao(): CountryDao

    abstract fun totalCasesDao(): TotalCasesDao
}