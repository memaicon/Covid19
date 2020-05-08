package br.com.tecdev.covid19.feature.credits

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.tecdev.covid19.R
import kotlinx.android.synthetic.main.activity_credits.*

class CreditsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)
        setOnClick()
    }

    private fun setOnClick() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        authorDataText.setOnClickListener {
            openUrl(authorData)
        }
        authorInfoText.setOnClickListener {
            openUrl(authorInfo)
        }
        authorApiText.setOnClickListener {
            openUrl(authorApi)
        }
        flaticonText.setOnClickListener {
            openUrl(flaticon)
        }
        freepikText.setOnClickListener {
            openUrl(freepik)
        }
        inspirationAuthorText.setOnClickListener {
            openUrl(inspirationAuthor)
        }
    }


    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    companion object {
        private const val authorData = "https://github.com/CSSEGISandData"
        private const val authorInfo = "https://www.who.int"
        private const val authorApi = "https://corona.lmao.ninja/"
        private const val flaticon = "https://www.flaticon.com/"
        private const val freepik = "https://br.freepik.com/"
        private const val inspirationAuthor = "https://www.instagram.com/ramonsl"
    }
}
