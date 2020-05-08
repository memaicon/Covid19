package br.com.tecdev.covid19.di.repository

import br.com.tecdev.covid19.repository.CovidRepository
import br.com.tecdev.covid19.repository.CovidRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<CovidRepository> { CovidRepositoryImpl(androidContext(), get(), get(), get()) }
}