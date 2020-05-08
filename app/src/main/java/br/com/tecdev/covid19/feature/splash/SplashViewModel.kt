package br.com.tecdev.covid19.feature.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crashlytics.android.Crashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import br.com.tecdev.covid19.BuildConfig
import br.com.tecdev.covid19.R
import br.com.tecdev.covid19.feature.base.BaseViewModel
import br.com.tecdev.covid19.repository.CovidRepository
import br.com.tecdev.covid19.util.getErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SplashViewModel(
    application: Application,
    private val covidRepository: CovidRepository,
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : BaseViewModel(application) {

    var showLoadingLiveData = MutableLiveData<Boolean>()

    var getDataSuccessfulLiveData = MutableLiveData<Boolean>()

    var getDataFailedLiveData = MutableLiveData<String>()

    fun getData() {
        showLoadingLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val configSettings =
                    FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(
                        if (BuildConfig.DEBUG) 0 else 3600
                    ).build()

                firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
                firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
                firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
                    getCovidData()
                }.addOnFailureListener {
                    Crashlytics.logException(it)
                }
            } catch (ex: Exception) {
                launch(Dispatchers.Main) {
                    showLoadingLiveData.postValue(false)
                    getDataFailedLiveData.postValue(getErrorMessage(context, ex))
                }
            }
        }
    }

    private fun getCovidData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                covidRepository.saveCovidData()

                launch(Dispatchers.Main) {
                    getDataSuccessfulLiveData.postValue(true)
                }
            } catch (ex: Exception) {
                launch(Dispatchers.Main) {
                    showLoadingLiveData.postValue(false)
                    getDataFailedLiveData.postValue(getErrorMessage(context, ex))
                }
            }
        }
    }
}