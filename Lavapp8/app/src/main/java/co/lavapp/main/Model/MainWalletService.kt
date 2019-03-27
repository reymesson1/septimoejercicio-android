package co.lavapp.main

import retrofit2.http.GET
import retrofit2.Call

interface MainWalletService {

    @GET("mainwallet")

    fun getMainWallet() : Call<List<WalletType>>
}