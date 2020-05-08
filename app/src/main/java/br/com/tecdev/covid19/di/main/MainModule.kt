package br.com.tecdev.covid19.di.main

import br.com.tecdev.covid19.di.data.dataModule
import br.com.tecdev.covid19.di.feature.*
import br.com.tecdev.covid19.di.repository.repositoryModule

val listModule = listOf(
    dataModule,
    repositoryModule,
    splashModule,
    homeModule,
    searchModule,
    globalCasesModule,
    firebaseModule,
    feedbackModule
)