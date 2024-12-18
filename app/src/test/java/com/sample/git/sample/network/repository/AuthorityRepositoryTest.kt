package com.sample.git.sample.network.repository

import com.sample.git.sample.network.model.login.oauth.AccessTokenModel
import com.sample.git.sample.network.service.AuthorityService
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class AuthorityRepositoryTest {

    private val authorityService = Mockito.mock(AuthorityService::class.java)
    private val errorResponse = "{\n" +
            "  \"type\": \"error\",\n" +
            "  \"message\": \"What you were looking for isn't here.\"\n" +
            "}"

    val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())


    @Before
    fun setUp() {

    }

    @Test
    fun loginPositive() = runBlocking {
        val loginResult = Mockito.mock(ResponseBody::class.java)
        val mockResponse = Response.success(loginResult)

        // WHEN
        Mockito.`when`(authorityService.login(
            clientId = "124",
            redirectUri = "www.google",
            scope = "",
            state = ""
        )).thenReturn( mockResponse )
        // THEN
        val login = authorityService.login(clientId = "124",
            redirectUri = "www.google",
            scope = "",
            state = "")
        assertEquals(mockResponse, login)
    }

//    @Test
//    fun loginNegative() = runBlocking {
//        val mockErrorResponse = Response.error<ResponseBody>(400, errorResponseBody)
//
//        // WHEN
//        Mockito.`when`(authorityService.login(
//            clientId = "124",
//            redirectUri = "www.google",
//            scope = "",
//            state = ""
//        )).thenReturn( mockErrorResponse )
//        // THEN
//        val login = authorityService.login(clientId = "124",
//            redirectUri = "www.google",
//            scope = "",
//            state = "")
//        assertEquals(mockErrorResponse, login)
//    }

    @Test
    fun getAccessToken() = runBlocking {
        val accessTokenModel = (AccessTokenModel(
            accessToken = "1234567",
            tokenType = "oAuth"
        ))
        val mockResponse = Response.success(accessTokenModel)

        // WHEN
        Mockito.`when`(authorityService.getAccessToken(
            clientId = "124",
            clientSecret = "abcdefg",
            code = "1234",
        )).thenReturn( mockResponse )
        // THEN
        val accessToken = authorityService.getAccessToken(
            clientId = "124",
            clientSecret = "abcdefg",
            code = "1234",
        )
        assertEquals(mockResponse, accessToken)
    }
}