package br.com.tecdev.covid19.util

import android.content.Context
import android.telephony.TelephonyManager

fun getCountryCode(context: Context): String {
    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return tm.networkCountryIso
}