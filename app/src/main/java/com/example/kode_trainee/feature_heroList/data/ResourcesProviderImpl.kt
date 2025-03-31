package com.example.kode_trainee.feature_heroList.data

import android.content.Context
import com.example.kode_trainee.R
import com.example.kode_trainee.feature_heroList.domain.ResourcesProvider

class ResourcesProviderImpl(private val context: Context) : ResourcesProvider {
    override fun getNothingFoundText(): String {
        return context.getString(R.string.nothing_found)
    }

    override fun getSomethingWentWrongText(): String {
        return context.getString(R.string.something_went_wrong)
    }
}