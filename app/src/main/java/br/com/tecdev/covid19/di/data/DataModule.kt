package br.com.tecdev.covid19.di.data

import android.content.Context
import androidx.room.Room
import br.com.tecdev.covid19.BuildConfig
import br.com.tecdev.covid19.data.database.AppDatabase
import br.com.tecdev.covid19.data.net.RestApi
import br.com.tecdev.covid19.data.session.SessionManager
import br.com.tecdev.covid19.data.session.SessionManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(RestApi.okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RestApi::class.java)
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single<SessionManager> {
        SessionManagerImpl(
            androidContext().getSharedPreferences(
                SessionManagerImpl.PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
        )
    }
}