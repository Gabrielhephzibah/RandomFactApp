package com.cherish.randomfactproject.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class StoreResponse(
    @PrimaryKey(autoGenerate = true)
    val uuid : Int?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val myId: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("rating")
    val rating: Rating?,
    @SerializedName("title")
    val title: String?
)


data class Rating(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("rate")
    val rate: Double?
)