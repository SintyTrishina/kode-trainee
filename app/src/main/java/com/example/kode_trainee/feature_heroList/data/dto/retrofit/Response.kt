package com.example.kode_trainee.feature_heroList.data.dto.retrofit

import com.example.kode_trainee.feature_heroList.data.dto.heroDto.HeroResponse

open class Response() {
    var resultCode = 0
    var results: List<HeroResponse> = emptyList()
}