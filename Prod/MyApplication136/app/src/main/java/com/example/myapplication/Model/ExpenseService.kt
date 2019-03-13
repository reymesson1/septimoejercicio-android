package com.example.myapplication.Model

import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.GET

interface ExpenseService {

    @GET("expense")

    fun getExpense() : Call<List<Wallet>>

}