package com.radhika.code.fidelity.data.api

import com.radhika.code.fidelity.data.model.SearchData
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

/*
Service call Implemetnation
 */
class ApiServiceImpl : ApiService {

    private val BASE_URL="https://api.jikan.moe/v3/search/anime"

    override fun getSearchData(searchtext:String): Single<SearchData> {
        return Rx2AndroidNetworking.get(BASE_URL).addQueryParameter("q", searchtext)
            .build()
            .getObjectSingle(SearchData::class.java)
    }
}