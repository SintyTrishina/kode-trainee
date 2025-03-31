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
                -1 -> Resource.Error("No internet", emptyList())
                200 -> {
                    val heroes = (response as? Response)?.results
                        ?.mapNotNull { it.toDomainOrNull() }
                        ?: emptyList()

                    if (heroes.isEmpty()) {
                        Resource.Error("Nothing found")
                    } else {
                        Resource.Success(heroes)
                    }
                }
                else -> Resource.Error("Server error", emptyList())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", emptyList())
        }
    }

    override fun filterHeroesByPublisher(publisher: String): Resource<List<Hero>> {
        val filtered = allHeroesCache.filter {
            it.biography.publisher.contains(publisher, ignoreCase = true)
        }

        return if (filtered.isEmpty()) {
            Resource.Error("Ничего не найдено")
        } else {
            Resource.Success(filtered)
        }
    }
}


