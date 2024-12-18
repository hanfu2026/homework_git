package com.sample.git.sample.network.service

import android.net.Uri
import com.sample.git.sample.network.constant.Endpoints
import com.sample.git.sample.network.model.login.oauth.AccessTokenModel
import com.sample.git.sample.network.model.login.oauth.AuthenticatedUser
import com.sample.git.sample.utils.getRandomString
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthorityService {
    @GET("login/oauth/authorize")
    suspend fun login(
        @Query("client_id") clientId: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("scope") scope: String,
        @Query("state") state: String,
    ): Response<ResponseBody>

    @Headers("Accept: application/json",)
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): Response<AccessTokenModel>


}

fun getAuthorizationUrl(): Uri {
    return Uri.Builder()
        .scheme("https")
        .authority("github.com")
        .appendPath("login")
        .appendPath("oauth")
        .appendPath("authorize")
        .appendQueryParameter("client_id", Endpoints.CLIENT_ID)
        .appendQueryParameter("redirect_uri", Endpoints.REDIRECT_URL)
        .appendQueryParameter("scope", Endpoints.SCOPE)
        .appendQueryParameter("state", getRandomString(16))
        .build()
}