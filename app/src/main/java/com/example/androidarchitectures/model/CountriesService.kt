package com.example.androidarchitectures.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService {
   companion object {
       val BASE_URL:String = "https://restcountries.com/v2/"
   }
    lateinit var contryApi: CountriesApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        contryApi = retrofit.create(CountriesApi::class.java)
    }
   fun getCountries(): Single<List<Country>> {
       return contryApi.getCountries()
   }
}