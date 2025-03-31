package com.example.kode_trainee.feature_heroList.data.network

import com.example.kode_trainee.feature_heroList.data.dto.retrofit.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroApi {
    @GET("{id}/biography")
    fun searchHeroes(@Path("id") term: String): Call<SearchResponse>

}



