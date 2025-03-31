package com.example.kode_trainee.feature_heroList.data.network

import com.example.kode_trainee.feature_heroList.data.dto.heroDto.HeroResponse
import retrofit2.Call
import retrofit2.http.GET

interface HeroApi {
    @GET("all.json")
    fun getAllHeroes(): Call<List<HeroResponse>>
}



