package br.com.tecdev.covid19.feature.global

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import br.com.tecdev.covid19.R
import br.com.tecdev.covid19.model.CountryResponse
import br.com.tecdev.covid19.model.TotalResponse
import br.com.tecdev.covid19.util.formatNumber
import kotlinx.android.synthetic.main.activity_global_cases.*
import org.koin.android.ext.android.inject

class GlobalCasesActivity : AppCompatActivity() {

    private val viewModel: GlobalCasesViewModel by inject()
    private val globalCasesAdapter: GlobalCasesAdapter by inject()
    private var country: CountryResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_cases)
        setViewModel()
        setOnClick()
        setRecycler()
        setCountrySelected()
    }

    private fun setViewModel() {
        viewModel.showTotalCasesLiveData.observe(this, observerTotalCases())
        viewModel.getCountriesLiveData.observe(this, observerGetCountries())
        viewModel.getLastUpdateLiveData.observe(this, observerLastUpdate())
        viewModel.getTotalCases()
    }

    private fun setOnClick() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecycler() {
        globalCasesRecycler.adapter = globalCasesAdapter
    }

    private fun setCountrySelected() {
        intent.extras?.run {
            country = getParcelable(COUNTRY_SELECTED)
        }
    }

    /** Observer **/

    private fun observerTotalCases() = Observer<TotalResponse> {
        totalCaseView.visibility = View.VISIBLE
        confirmedCountText.text = formatNumber(it.cases)
        deathCountText.text = formatNumber(it.deaths)
        recoveredCountText.text = formatNumber(it.recovered)
    }

    private fun observerLastUpdate() = Observer<String> {
        updateText.visibility = View.VISIBLE
        updateText.text = getString(R.string.update_at, it)
    }

    private fun observerGetCountries() = Observer<MutableList<CountryResponse>> {
        globalCasesAdapter.setData(it)
        if (country != null) {
            val position = globalCasesAdapter.getPositionFromCountry(country!!.country)
            position?.run {
                globalCasesAdapter.listSelected?.set(this, true)
                globalCasesRecycler.scrollToPosition(this)
            }
        } else {
            globalCasesAdapter.notifyDataSetChanged()
        }
        loadView.visibility = View.GONE
    }

    companion object {
        const val COUNTRY_SELECTED = "country_selected"
    }
}
