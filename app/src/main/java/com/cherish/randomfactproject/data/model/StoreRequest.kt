package com.cherish.randomfactproject.data.model
import com.google.gson.annotations.SerializedName

data class StoreRequest(
    @SerializedName("category")
    val category: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("title")
    val title: String?
)