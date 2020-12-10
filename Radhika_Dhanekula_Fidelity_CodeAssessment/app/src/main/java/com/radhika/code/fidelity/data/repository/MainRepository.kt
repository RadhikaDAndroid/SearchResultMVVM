package com.radhika.code.fidelity.data.repository

import com.radhika.code.fidelity.data.api.ApiHelper
import com.radhika.code.fidelity.data.model.SearchData
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUsers(searchtext:String): Single<SearchData> {
        return apiHelper.getUsers(searchtext)
    }
}