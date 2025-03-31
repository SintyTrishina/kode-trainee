package com.example.kode_trainee.feature_heroList.domain.api

import com.example.kode_trainee.feature_heroList.domain.models.Hero

interface HeroListInteractor {
    fun getHeroesByPublisher(term: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(data: List<Hero>?, errorMessage: String?)
    }
}