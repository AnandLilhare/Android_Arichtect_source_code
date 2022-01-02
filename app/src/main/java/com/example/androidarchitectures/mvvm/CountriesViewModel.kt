package com.example.androidarchitectures.mvvm

import androidx.lifecycle.ViewModel
import com.example.androidarchitectures.model.CountriesService
import com.example.androidarchitectures.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData


class CountriesViewModel : ViewModel() {

    private lateinit var countriesService: CountriesService
    private val countries = MutableLiveData<List<String>>()
    private val countryError = MutableLiveData<Boolean>()

    init {
        countriesService = CountriesService()
        fetchCountries()
    }

    fun getCountries(): LiveData<List<String>> {
        return countries
    }

    fun getCountryError(): LiveData<Boolean> {
        return countryError
    }

    private fun fetchCountries() {
        countriesService.getCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                override fun onSuccess(value: List<Country>?) {
                    var countryList = ArrayList<String>()
                    if (value != null) {
                        for(item in value){
                            countryList.add(item.countryName)
                        }
                    }
                    countries.value = countryList
                    countryError.value = false

                }

                override fun onError(e: Throwable?) {
                    countryError.value = true
                }
            })
    }

    fun onRefresh() {
        fetchCountries()
    }
}