package com.example.kode_trainee.feature_heroList.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hero(
    val id: String,
    val name: String,
    val biography: Biography,
    val powerstats: PowerStats,
    val images: String,
    val appearance: Appearance? = null,
    val connections: Connections? = null,
    val work: Work? = null,
    var isFavorite: Boolean = false
) : Parcelable {

    @Parcelize
    data class Biography(
        val publisher: String,
        val alignment: String,
        val fullName: String,
        val alterEgos: String? = null,
        val aliases: List<String>? = null,
        val placeOfBirth: String? = null,
        val firstAppearance: String? = null
    ) : Parcelable

    @Parcelize
    data class PowerStats(
        val intelligence: String,
        val strength: String,
        val speed: String,
        val durability: String,
        val power: String,
        val combat: String
    ) : Parcelable

    @Parcelize
    data class Appearance(
        val gender: String,
        val race: String,
        val height: List<String>,
        val weight: List<String>,
        val eyeColor: String,
        val hairColor: String
    ) : Parcelable

    @Parcelize
    data class Connections(
        val groupAffiliation: String,
        val relatives: String
    ) : Parcelable

    @Parcelize
    data class Work(
        val occupation: String,
        val base: String
    ) : Parcelable
}
