package com.example.kode_trainee.feature_heroList.presentation.di

import com.example.kode_trainee.feature_heroList.presentation.viewmodel.FavoriteViewModel
import com.example.kode_trainee.feature_heroList.presentation.viewmodel.HeroListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val heroListViewModelModule = module {

    viewModel {
        HeroListViewModel(get(), get())
    }

    viewModel {
        FavoriteViewModel()
    }
}