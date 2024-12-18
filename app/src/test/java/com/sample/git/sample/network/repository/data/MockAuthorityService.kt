package com.sample.git.sample.network.repository.data

import com.sample.git.sample.network.model.login.oauth.AccessTokenModel
import com.sample.git.sample.network.service.AuthorityService
import okhttp3.ResponseBody
import retrofit2.Response

class MockAuthorityService: AuthorityService {
    private val accessToken = AccessTokenModel(
        tokenType = "oAuth",
        accessToken = "1234"
    )
    override suspend fun login(
        clientId: String,
        redirectUri: String,
        scope: String,
        state: String
    ): Response<ResponseBody> {
        ResponseBody
    }

    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Response<AccessTokenModel> {
        return Response<AccessTokenModel>(accessToken, ResponseBody())
    }
}