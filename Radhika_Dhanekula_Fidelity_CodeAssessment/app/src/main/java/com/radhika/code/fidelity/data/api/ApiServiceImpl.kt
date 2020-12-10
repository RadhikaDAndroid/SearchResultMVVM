package com.radhika.code.fidelity.data.api

import com.radhika.code.fidelity.data.model.SearchData
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImpl : ApiService {

    override fun getUsers(searchtext:String): Single<SearchData> {
        return Rx2AndroidNetworking.get("https://api.jikan.moe/v3/search/anime").addQueryParameter("q", searchtext)
            .build()
            .getObjectSingle(SearchData::class.java)
    }
}