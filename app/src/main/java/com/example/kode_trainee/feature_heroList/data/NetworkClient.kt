package com.example.kode_trainee.feature_heroList.data

import com.example.kode_trainee.feature_heroList.data.dto.retrofit.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}