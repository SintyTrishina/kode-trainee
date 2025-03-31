package com.example.kode_trainee.feature_heroList.domain.di

import com.example.kode_trainee.feature_heroList.domain.api.HeroListInteractor
import com.example.kode_trainee.feature_heroList.domain.impl.HeroListInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<HeroListInteractor> {
        HeroListInteractorImpl(get())
    }

}