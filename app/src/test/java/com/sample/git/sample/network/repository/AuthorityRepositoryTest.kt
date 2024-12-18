package com.sample.git.sample.network.repository

import com.sample.git.sample.network.RetrofitInstance
import com.sample.git.sample.network.service.AuthorityService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit

class AuthorityRepositoryTest {

    private val authorityService: AuthorityService = RetrofitInstance.authorityService

    @Before
    fun setUp() {
        val errorResponse =
            "{\n" +
                    "  \"type\": \"error\",\n" +
                    "  \"message\": \"What you were looking for isn't here.\"\n" +
                    "}"
        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
        val mockResponse = Response.error<String>(400, errorResponseBody)
    }

    @Test
    fun login() {

    }

    @Test
    fun getAccessToken() {

        Retrofit.Builder.
   
    }
}