package com.example.myapplication.Model

import retrofit2.http.GET
import retrofit2.Call

interface PostService {

    @GET("master")
    fun response() : Call<List<Post>>
}