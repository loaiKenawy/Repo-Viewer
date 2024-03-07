package com.vf.task.repoviewer.data.remote

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {

        private const val TAG = "RetrofitClient"
        private const val BASE_URL = "https://api.github.com/"

        private var retrofitInstance: GitHubService? = null

        fun getInstance(): GitHubService? {
            try {
                if (retrofitInstance == null) {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .build()
                    retrofitInstance = retrofit.create(GitHubService::class.java)
                }
                Log.i(TAG, "Instance created")
            } catch (e: Exception) {
                Log.e(TAG, "Can't get retrofit instance: ${e.message} ")
            }

            return retrofitInstance
        }
    }

}