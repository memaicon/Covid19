package br.com.tecdev.covid19.util.location

import android.location.Location

interface OnGetLocationSuccessful {
    fun onGetLocationSuccessful(location: Location)
}