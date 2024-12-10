package com.nursaputrayow.artefacto.network

import com.nursaputrayow.artefacto.model.RegisterRequest
import com.nursaputrayow.artefacto.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface AuthApi {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}