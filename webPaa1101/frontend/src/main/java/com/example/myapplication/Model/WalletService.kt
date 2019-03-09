package com.example.myapplication.Model

import retrofit2.http.GET
import retrofit2.Call

interface WalletService {

    @GET("wallet")

    fun getWallet() : Call<List<Wallet>>
}