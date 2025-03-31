package com.example.kode_trainee.feature_heroList.data.dto.retrofit

import com.example.kode_trainee.feature_heroList.data.dto.heroDto.HeroDto

data class SearchResponse(
                          val results: List<HeroDto>): Response()
