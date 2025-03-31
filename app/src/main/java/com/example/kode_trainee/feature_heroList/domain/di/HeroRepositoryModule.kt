package com.example.kode_trainee.feature_heroList.domain.di

import com.example.kode_trainee.feature_heroList.data.dto.HeroRepositoryImpl
import com.example.kode_trainee.feature_heroList.domain.api.HeroRepository
import org.koin.dsl.module

val heroRepositoryModule = module {
    single<HeroRepository> {
        HeroRepositoryImpl(get())
    }
}