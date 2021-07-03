package com.example.retrofitlearning.service

import com.example.retrofitlearning.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    @GET("prices?key=408ee87da1f92d66ede58b17551016ddcac0c70a")
    fun getData(): Observable<List<CryptoModel>>

    //fun getData(): Call<List<CryptoModel>>

}