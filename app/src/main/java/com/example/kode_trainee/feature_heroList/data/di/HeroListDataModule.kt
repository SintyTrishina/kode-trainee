package com.example.kode_trainee.feature_heroList.data.di

import com.example.kode_trainee.feature_heroList.data.NetworkClient
import com.example.kode_trainee.feature_heroList.data.ResourcesProviderImpl
import com.example.kode_trainee.feature_heroList.data.network.ApiConfig
import com.example.kode_trainee.feature_heroList.data.network.HeroApi
import com.example.kode_trainee.feature_heroList.data.network.RetrofitNetworkClient
import com.example.kode_trainee.feature_heroList.domain.ResourcesProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val heroListDataModule = module {

    single<HeroApi> {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeroApi::class.java)
    }
    single<NetworkClient> {
        RetrofitNetworkClient(androidContext(), get())
    }

    single<ResourcesProvider> {
        ResourcesProviderImpl(androidContext())
    }
}