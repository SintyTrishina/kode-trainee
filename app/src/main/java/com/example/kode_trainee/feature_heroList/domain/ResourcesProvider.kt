package com.example.kode_trainee.feature_heroList.domain

interface ResourcesProvider {
    fun getNothingFoundText(): String
    fun getSomethingWentWrongText(): String
}