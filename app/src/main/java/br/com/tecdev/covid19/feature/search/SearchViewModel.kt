package br.com.tecdev.covid19.feature.search

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crashlytics.android.Crashlytics
import br.com.tecdev.covid19.R
import br.com.tecdev.covid19.feature.base.BaseViewModel
import br.com.tecdev.covid19.model.CountryInfo
import br.com.tecdev.covid19.model.CountryResponse
import br.com.tecdev.covid19.repository.CovidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application, private val covidRepository: CovidRepository) :
    BaseViewModel(application) {

    var searchCountriesLiveData = MutableLiveData<MutableList<CountryResponse>>()

    fun search(q: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countries = covidRepository.getCountriesFromName(q)
                launch(Dispatchers.Main) {
                    searchCountriesLiveData.postValue(countries)
                }
            } catch (ex: Exception) {
                Crashlytics.logException(ex)
            }
        }
    }

    fun getContries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countries = covidRepository.getCountries()
                countries.reverse()
                val countryInfoNow = CountryInfo()
                val locationNow =
                    CountryResponse(
                        context.getString(R.string.your_location),
                        countryInfoNow,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0.0,
                        0.0
                    )
                countries.add(locationNow)
                countries.reverse()
                launch(Dispatchers.Main) {
                    searchCountriesLiveData.postValue(countries)
                }
            } catch (ex: Exception) {
                Crashlytics.logException(ex)
            }
        }
    }
}