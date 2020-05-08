package br.com.tecdev.covid19.util.location

import java.lang.Exception

interface OnGetLocationFailed {
    fun onGetLocationFailed(exception: Exception)
}