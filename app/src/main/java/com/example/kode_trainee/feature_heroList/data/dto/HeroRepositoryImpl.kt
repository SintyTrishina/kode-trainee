package com.example.kode_trainee.feature_heroList.data.dto

import com.example.kode_trainee.feature_heroList.data.NetworkClient
import com.example.kode_trainee.feature_heroList.data.dto.retrofit.SearchRequest
import com.example.kode_trainee.feature_heroList.data.dto.retrofit.SearchResponse
import com.example.kode_trainee.feature_heroList.domain.api.HeroRepository
import com.example.kode_trainee.feature_heroList.domain.models.Hero
import com.example.kode_trainee.utils.Resource

class HeroRepositoryImpl(
    private val networkClient: NetworkClient
) : HeroRepository {
    override fun getHeroesByPublisher(term: String): Resource<List<Hero>> {
        val response = networkClient.doRequest(SearchRequest(term))

        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету", emptyList())
            }

            200 -> {
                Resource.Success((response as SearchResponse).results.map {
                    Hero(
                        it.appearance,
                        it.biography,
                        it.connections,
                        it.name,
                        it.image,
                        it.id,
                        it.powerstats,
                        it.response,
                        it.work
                    )
                })
            }

            else -> {
                Resource.Error("Ошибка сервера", emptyList())
            }
        }
    }
}
