package com.example.myapplication.Model

import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.Call

interface MainWalletService {

    @GET("mainwallet")

    fun getMainWallet() : Call<List<WalletType>>
}