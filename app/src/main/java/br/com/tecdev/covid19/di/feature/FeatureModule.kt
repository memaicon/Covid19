package br.com.tecdev.covid19.di.feature

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import br.com.tecdev.covid19.feature.feedback.FeedbackViewModel
import br.com.tecdev.covid19.feature.global.GlobalCasesAdapter
import br.com.tecdev.covid19.feature.global.GlobalCasesViewModel
import br.com.tecdev.covid19.feature.home.HomeViewModel
import br.com.tecdev.covid19.feature.search.SearchAdapter
import br.com.tecdev.covid19.feature.search.SearchViewModel
import br.com.tecdev.covid19.feature.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    viewModel { SplashViewModel(androidApplication(), get(), get()) }
}

val homeModule = module {
    viewModel { HomeViewModel(androidApplication(), get(), get()) }
}

val searchModule = module {
    viewModel { SearchViewModel(androidApplication(), get()) }
    factory { SearchAdapter() }
}

val globalCasesModule = module {
    viewModel { GlobalCasesViewModel(get()) }
    factory { GlobalCasesAdapter() }
}

val firebaseModule = module {
    single { FirebaseRemoteConfig.getInstance() }
    single { FirebaseFirestore.getInstance() }
}

val feedbackModule = module {
    viewModel { FeedbackViewModel(androidApplication(), get()) }
}