package com.example.myapplication.Model

import org.json.JSONObject
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body

interface RemoveWalletService {

    @POST("removewalletandroid")

    fun removeWallet(@Body remove : JSONObject ) : Call<String>
}