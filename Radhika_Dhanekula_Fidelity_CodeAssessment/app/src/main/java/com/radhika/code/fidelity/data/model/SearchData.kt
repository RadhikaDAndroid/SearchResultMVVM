package com.radhika.code.fidelity.data.model

import com.google.gson.annotations.SerializedName

/*
Search api Response
 */
data class SearchData
    (
    @SerializedName("request_hash") val requestHash: String,
    @SerializedName("request_cached") val requestCached: Boolean,
    @SerializedName("request_cache_expiry") val requestCache_expiry: Long,
    @SerializedName("results") val results: ArrayList<SearchResult>,
    @SerializedName("last_page") val lastPage: Int
)