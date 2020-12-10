package com.radhika.code.fidelity.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getUsers(searchtext:String) = apiService.getUsers(searchtext)

}