package com.example.kode_trainee.feature_heroList.domain.models

import com.example.kode_trainee.feature_heroList.data.dto.heroDto.Appearance
import com.example.kode_trainee.feature_heroList.data.dto.heroDto.Biography
import com.example.kode_trainee.feature_heroList.data.dto.heroDto.Connections
import com.example.kode_trainee.feature_heroList.data.dto.heroDto.Image
import com.example.kode_trainee.feature_heroList.data.dto.heroDto.Powerstats
import com.example.kode_trainee.feature_heroList.data.dto.heroDto.Work

data class Hero(
    val appearance: Appearance,
    val biography: Biography,
    val connections: Connections,
    val id: String,
    val image: Image,
    val name: String,
    val powerstats: Powerstats,
    val response: String,
    val work: Work
)
