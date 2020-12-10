package com.radhika.code.fidelity.data.api

import com.radhika.code.fidelity.data.model.SearchData
import io.reactivex.Single

interface ApiService {

    fun getUsers(searchtext:String): Single<SearchData>

}