package com.example.kode_trainee.feature_heroList.data.dto

import com.example.kode_trainee.feature_heroList.data.NetworkClient
import com.example.kode_trainee.feature_heroList.data.dto.retrofit.Response
import com.example.kode_trainee.feature_heroList.data.dto.retrofit.SearchRequest
import com.example.kode_trainee.feature_heroList.domain.api.HeroRepository
import com.example.kode_trainee.feature_heroList.domain.models.Hero
import com.example.kode_trainee.utils.Resource

class HeroRepositoryImpl(
    private val networkClient: NetworkClient
) : HeroRepository {
    private var allHeroesCache: List<Hero> = emptyList()

    override fun getHeroesByPublisher(term: String): Resource<List<Hero>> {
        return try {
            val response = networkClient.doRequest(SearchRequest(term))

            when (response.resultCode) {
                -1 -> Resource.Error("Нет интернета", emptyList())
                200 -> {
                    val heroes = (response as? Response)?.results
                        ?.mapNotNull { it.toDomainOrNull() }
                        ?: emptyList()

                    allHeroesCache = heroes

                    if (term.isEmpty()) {
                        Resource.Success(heroes)
                    } else {
                        val filtered = heroes.filter {
                            it.biography.publisher.contains(term, ignoreCase = true)
                        }

                        if (filtered.isEmpty()) {
                            Resource.Error("Ничего не нашлось")
                        } else {
                            Resource.Success(filtered)
                        }
                    }
                }

                else -> Resource.Error("Ошибка сервера", emptyList())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Неизвестная ошибка", emptyList())
        }
    }
}

