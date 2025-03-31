package com.example.kode_trainee.feature_heroList.presentation.viewmodel

import com.example.kode_trainee.feature_heroList.domain.models.Hero

sealed interface State {
    object Loading : State

    data class Content(
        val heroes: List<Hero>
    ) : State

    data class Error(
        val errorMessage: String
    ) : State

    data class Empty(
        val message: String
    ) : State
}