package com.example.kode_trainee.feature_heroList.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.kode_trainee.feature_heroList.data.NetworkClient
import com.example.kode_trainee.feature_heroList.data.dto.retrofit.Response
import com.example.kode_trainee.feature_heroList.data.dto.retrofit.SearchRequest

class RetrofitNetworkClient(
    private val context: Context,
    private val apiService: HeroApi
) : NetworkClient {

    override fun doRequest(dto: Any): Response {

        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }

        if (dto is SearchRequest) {
            val resp = apiService.searchHeroes(dto.term).execute()

            val body = resp.body() ?: Response()

            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }


    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}