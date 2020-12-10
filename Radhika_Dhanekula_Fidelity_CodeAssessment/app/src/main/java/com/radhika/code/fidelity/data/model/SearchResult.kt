package com.radhika.code.fidelity.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class SearchResult(
    @SerializedName("mal_id") val malId: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("airing") val airing: Boolean,
    @SerializedName("synopsis") val synopsis: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("episodes") val episodes: Int,
    @SerializedName("score") val score: Double,
    @SerializedName("start_date") val startDate: Date,
    @SerializedName("end_date") val endDate: Date,
    @SerializedName("members") val members: Int,
    @SerializedName("rated") val rated: String?
)