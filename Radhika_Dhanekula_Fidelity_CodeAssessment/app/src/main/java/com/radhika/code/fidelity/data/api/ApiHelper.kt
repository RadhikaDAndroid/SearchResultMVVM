package com.radhika.code.fidelity.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getSearchDataHelper(searchtext:String) = apiService.getSearchData(searchtext)

}