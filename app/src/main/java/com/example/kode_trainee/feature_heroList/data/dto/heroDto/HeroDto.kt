package com.example.kode_trainee.feature_heroList.data.dto.heroDto

data class HeroDto(
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