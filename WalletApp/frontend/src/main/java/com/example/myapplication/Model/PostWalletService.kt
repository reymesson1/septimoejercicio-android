package com.example.myapplication.Model

import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface PostWalletService {

    @POST("addwalletandroid")

    fun setWallet(@Body json : JSONObject) : Call<String>
}