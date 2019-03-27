package co.lavapp.main

import retrofit2.Call
import retrofit2.http.GET

interface ExpenseService {

    @GET("expense")

    fun getExpense() : Call<List<Wallet>>

}