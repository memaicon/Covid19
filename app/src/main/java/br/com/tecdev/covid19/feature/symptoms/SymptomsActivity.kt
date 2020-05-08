package br.com.tecdev.covid19.feature.symptoms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.tecdev.covid19.R
import br.com.tecdev.covid19.feature.emergency.EmergencyActivity
import br.com.tecdev.covid19.firebase.analytics.AnalyticsUtil
import kotlinx.android.synthetic.main.activity_symptoms.*

class SymptomsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms)
        setOnClick()
    }

    private fun setOnClick() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        knowMoreButton.setOnClickListener {
            AnalyticsUtil.logEvent(this, AnalyticsUtil.Value.SYMPTOMS_EMERGENCY)
            goToEmergencyGuide()
        }
    }

    private fun goToEmergencyGuide() {
        startActivity(Intent(this, EmergencyActivity::class.java))
    }
}
