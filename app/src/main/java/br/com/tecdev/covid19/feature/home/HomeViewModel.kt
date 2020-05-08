package br.com.tecdev.covid19.feature.home

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crashlytics.android.Crashlytics
import com.google.android.gms.maps.model.Marker
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import br.com.tecdev.covid19.BuildConfig
import br.com.tecdev.covid19.R
import br.com.tecdev.covid19.feature.base.BaseViewModel
import br.com.tecdev.covid19.model.CountryResponse
import br.com.tecdev.covid19.model.TotalResponse
import br.com.tecdev.covid19.repository.CovidRepository
import br.com.tecdev.covid19.service.DownloadService
import br.com.tecdev.covid19.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application,
    private val covidRepository: CovidRepository,
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : BaseViewModel(application) {

    var showTotalCasesLiveData = MutableLiveData<TotalResponse>()

    var getCountriesLiveData = MutableLiveData<MutableList<CountryResponse>>()

    var showUpdateDialogLiveData = MutableLiveData<Boolean>()

    var showOpenApkDialogLiveData = MutableLiveData<String>()

    var downloadFailedLiveData = MutableLiveData<String>()

    val data = HashMap<Marker, CountryResponse>()

    fun checkVersionApp() {
        viewModelScope.launch(Dispatchers.IO) {
            val appVersion = BuildConfig.VERSION_CODE
            val newVersion = firebaseRemoteConfig.getLong(BuildConfig.KEY_VERSION_CODE)
            if (newVersion > appVersion) {
                launch(Dispatchers.Main) {
                    showUpdateDialogLiveData.postValue(true)
                }
            }
        }
    }

    fun getTotalCases() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val totalCases = covidRepository.getTotalCases()
                launch(Dispatchers.Main) {
                    showTotalCasesLiveData.postValue(totalCases)
                }
            } catch (ex: Exception) {
                Crashlytics.logException(ex)
            }
        }
    }

    fun getCountryCases() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countryCases = covidRepository.getCountries()
                launch(Dispatchers.Main) {
                    getCountriesLiveData.postValue(countryCases)
                }
            } catch (ex: Exception) {
                Crashlytics.logException(ex)
            }
        }
    }

    fun downloadAppNewVersion() {
        if (NetworkUtil.isOnline(context)) {
            val downloadUrl = firebaseRemoteConfig.getString(BuildConfig.KEY_FILE_DOWNLOAD)

            val intent = Intent(context, DownloadService::class.java)
            intent.putExtra(DownloadService.DOWNLOAD_FILE, downloadUrl)

            context.startService(intent)
            context.registerReceiver(
                receiver,
                IntentFilter(DownloadService.RECEIVER_DOWNLOAD_SERVICE)
            )
        } else {
            downloadFailedLiveData.postValue(context.getString(R.string.network_error_message))
        }

    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras
            if (bundle != null) {
                val destination = bundle.getString(DownloadService.DESTINATION)
                showOpenApkDialogLiveData.postValue(destination)
            }
        }

    }
}