package com.example.kode_trainee.feature_heroList.domain.api

import com.example.kode_trainee.feature_heroList.domain.models.Hero
import com.example.kode_trainee.utils.Resource

interface HeroRepository {
    fun getHeroesByPublisher(term: String): Resource<List<Hero>>
}