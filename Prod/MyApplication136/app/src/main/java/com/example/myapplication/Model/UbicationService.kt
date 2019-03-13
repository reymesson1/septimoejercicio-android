package com.example.myapplication.Model

import org.json.JSONObject
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body

interface UbicationService {

    @POST("ubication")
    fun setUbication(@Body data : JSONObject) : Call<String>
}