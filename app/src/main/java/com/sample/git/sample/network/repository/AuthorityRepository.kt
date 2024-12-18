package com.sample.git.sample.network.repository

import com.sample.git.sample.network.RetrofitInstance
import com.sample.git.sample.network.constant.Endpoints
import com.sample.git.sample.network.model.login.oauth.AccessTokenModel
import com.sample.git.sample.utils.getRandomString
import okhttp3.ResponseBody
import retrofit2.Response

class AuthorityRepository {
    private val authorityService = RetrofitInstance.authorityService

    suspend fun login(): Response<ResponseBody> {
        return authorityService.login(
            clientId = Endpoints.CLIENT_ID,
            redirectUri = Endpoints.REDIRECT_URL,
            scope = Endpoints.SCOPE,
            state = getRandomString(16)
        )
    }

    suspend fun getAccessToken(code: String): Response<AccessTokenModel> {
        return authorityService.getAccessToken(
            clientId = Endpoints.CLIENT_ID,
            clientSecret = Endpoints.CLIENT_SECRET,
            code = code
        )
    }

}

