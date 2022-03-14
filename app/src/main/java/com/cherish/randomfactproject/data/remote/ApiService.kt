package com.cherish.randomfactproject.data.remote

import com.cherish.randomfactproject.data.model.AddStoreResponse
import com.cherish.randomfactproject.data.model.StoreRequest
import com.cherish.randomfactproject.data.model.StoreResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("products")
    suspend fun getAllStoreItem() : List<StoreResponse>

    @POST("products")
    suspend fun addStoreItem(
        @Body item : StoreRequest
    ) : AddStoreResponse
}