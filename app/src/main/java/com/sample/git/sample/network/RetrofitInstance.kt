package com.sample.git.sample.network

import com.google.gson.GsonBuilder
import com.sample.git.sample.network.constant.Endpoints
import com.sample.git.sample.network.service.AuthorityService
import com.sample.git.sample.network.service.GithubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {
    private val retrofit: Retrofit by lazy {
        createRetrofit(Endpoints.BASE_URL)
    }

    private val authRetrofit: Retrofit by lazy {
        createRetrofit(Endpoints.BASIC_AUTH_URL)
    }

    private val htmlRetrofit: Retrofit by lazy {
        createRetrofit(Endpoints.TRENDING_URL )
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        //show Network information in to the logcat
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                // time out setting
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(25,TimeUnit.SECONDS)
        }.build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
    }

    val githubService: GithubService by lazy {
        retrofit.create(GithubService::class.java)
    }

    val authorityService: AuthorityService by lazy {
        authRetrofit.create(AuthorityService::class.java)
    }
}