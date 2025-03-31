package com.example.kode_trainee.utils

import android.app.Application
import com.example.kode_trainee.feature_heroList.data.di.heroListDataModule
import com.example.kode_trainee.feature_heroList.domain.di.heroRepositoryModule
import com.example.kode_trainee.feature_heroList.domain.di.interactorModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                heroRepositoryModule,
                heroListDataModule,
                interactorModule,
            )
        }
    }
}