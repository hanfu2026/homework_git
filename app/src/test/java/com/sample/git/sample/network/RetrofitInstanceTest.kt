package com.sample.git.sample.network

import org.junit.Assert.assertNotNull
import org.junit.Test

class RetrofitInstanceTest {
    private val authorityService = RetrofitInstance.authorityService
    private val githubService = RetrofitInstance.githubService

    @Test
    fun getGithubService() {
        assertNotNull(authorityService)
    }

    @Test
    fun getAuthorityService() {
        assertNotNull(githubService)
    }
}