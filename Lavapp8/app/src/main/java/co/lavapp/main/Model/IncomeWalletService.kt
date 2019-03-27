package co.lavapp.main

import retrofit2.http.GET
import retrofit2.Call

interface IncomeWalletService {

    @GET("income")

    fun getIncome() : Call<List<Wallet>>
}