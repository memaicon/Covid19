package br.com.tecdev.covid19.repository

import br.com.tecdev.covid19.model.CountryResponse
import br.com.tecdev.covid19.model.TotalResponse

interface CovidRepository {

    suspend fun saveCovidData()

    suspend fun getCountries(): MutableList<CountryResponse>

    suspend fun getCountriesByConfirmedCases(): MutableList<CountryResponse>

    suspend fun getCountriesFromName(name: String): MutableList<CountryResponse>

    suspend fun getTotalCases(): TotalResponse

    suspend fun getLastUpdate(): String
}