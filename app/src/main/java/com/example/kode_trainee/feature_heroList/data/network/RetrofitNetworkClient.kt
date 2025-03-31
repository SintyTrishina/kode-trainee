package com.example.kode_trainee.feature_heroList.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.kode_trainee.feature_heroList.data.NetworkClient
import com.example.kode_trainee.feature_heroList.data.dto.retrofit.Response

class RetrofitNetworkClient(
    private val context: Context,
    private val apiService: HeroApi
) : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }

        return try {
            val call = apiService.getAllHeroes()
            val response = call.execute()

            Response().apply {
                resultCode = response.code()
                results = response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            Response().apply { resultCode = 500 }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}


