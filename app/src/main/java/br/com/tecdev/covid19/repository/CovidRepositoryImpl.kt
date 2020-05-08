package br.com.tecdev.covid19.repository

import android.content.Context
import br.com.tecdev.covid19.R
import br.com.tecdev.covid19.data.database.AppDatabase
import br.com.tecdev.covid19.data.net.RestApi
import br.com.tecdev.covid19.data.session.SessionManager
import br.com.tecdev.covid19.exception.NetworkException
import br.com.tecdev.covid19.model.CountryResponse
import br.com.tecdev.covid19.model.TotalResponse
import br.com.tecdev.covid19.util.NetworkUtil
import br.com.tecdev.covid19.util.formatDateFromMillis

class CovidRepositoryImpl(
    private val context: Context,
    private val restApi: RestApi,
    private val database: AppDatabase,
    private val sessionManager: SessionManager
) : CovidRepository {

    override suspend fun saveCovidData() {
        if (NetworkUtil.isOnline(context)) {

            val currentResponse = restApi.getCurrent()

            database.countryDao().deleteAll()
            database.countryDao().insertAll(currentResponse)

            val totalResponse = restApi.getTotal()
            totalResponse.id = totalResponse.javaClass.simpleName
            sessionManager.lastDateUpdate = formatDateFromMillis(totalResponse.updated)
            database.totalCasesDao().insert(totalResponse)
        } else {
            if (database.countryDao().getCountry().isEmpty()) {
                throw NetworkException(context.getString(R.string.network_error_message))
            }
        }
    }

    override suspend fun getCountries(): MutableList<CountryResponse> {
        return database.countryDao().getCountry()
    }

    override suspend fun getCountriesByConfirmedCases(): MutableList<CountryResponse> {
        return database.countryDao().getCountryByConfirmedCases()
    }

    override suspend fun getCountriesFromName(name: String): MutableList<CountryResponse> {
        return database.countryDao().getCountry(name)
    }

    override suspend fun getTotalCases(): TotalResponse {
        return database.totalCasesDao().getTotalCases()
    }

    override suspend fun getLastUpdate(): String {
        return sessionManager.lastDateUpdate
    }
}