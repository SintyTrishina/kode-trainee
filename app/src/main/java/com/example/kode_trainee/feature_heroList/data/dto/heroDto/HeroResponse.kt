package com.example.kode_trainee.feature_heroList.data.dto.heroDto

import android.util.Log
import com.example.kode_trainee.feature_heroList.domain.models.Hero

data class HeroResponse(
    val response: String,
    val id: String,
    val name: String,
    val powerstats: PowerStatsResponse,
    val biography: BiographyResponse,
    val images: ImageResponse?,
    val appearance: AppearanceResponse? = null,
    val connections: ConnectionsResponse? = null,
    val work: WorkResponse? = null
) {
    fun toDomainOrNull(): Hero? = try {
        Hero(
            id = id,
            name = name,
            powerstats = powerstats.toDomain(),
            biography = biography.toDomain(),
            images = images?.lg ?: "",
            appearance = appearance?.toDomain(),
            connections = connections?.toDomain(),
            work = work?.toDomain()
        )
    } catch (e: Exception) {
        Log.e("HeroResponse", "Error converting hero: ${e.message}")
        null
    }
}

data class PowerStatsResponse(
    val intelligence: String,
    val strength: String,
    val speed: String,
    val durability: String,
    val power: String,
    val combat: String
) {
    fun toDomain() = Hero.PowerStats(
        intelligence = intelligence,
        strength = strength,
        speed = speed,
        durability = durability,
        power = power,
        combat = combat
    )
}

data class BiographyResponse(
    val publisher: String?,
    val alignment: String?,
    val fullName: String?,
    val alterEgos: String?,
    val aliases: List<String>?,
    val placeOfBirth: String?,
    val firstAppearance: String?
) {
    fun toDomain() = Hero.Biography(
        publisher = publisher ?: "Unknown",
        alignment = alignment ?: "Unknown",
        fullName = fullName ?: "Unknown",
        alterEgos = alterEgos ?: "Unknown",
        aliases = aliases ?: emptyList(),
        placeOfBirth = placeOfBirth ?: "Unknown",
        firstAppearance = firstAppearance ?: "Unknown"
    )
}

data class AppearanceResponse(
    val gender: String?,
    val race: String?,
    val height: List<String>?,
    val weight: List<String>?,
    val eyeColor: String?,
    val hairColor: String?
) {
    fun toDomain() = Hero.Appearance(
        gender = gender ?: "Unknown",
        race = race ?: "Unknown",
        height = height ?: emptyList(),
        weight = weight ?: emptyList(),
        eyeColor = eyeColor ?: "Unknown",
        hairColor = hairColor ?: "Unknown"
    )
}

data class ConnectionsResponse(
    val groupAffiliation: String,
    val relatives: String
) {
    fun toDomain() = Hero.Connections(
        groupAffiliation = groupAffiliation,
        relatives = relatives
    )
}

data class WorkResponse(
    val occupation: String,
    val base: String
) {
    fun toDomain() = Hero.Work(
        occupation = occupation,
        base = base
    )
}

data class ImageResponse(
    val xs: String,
    val lg: String
)