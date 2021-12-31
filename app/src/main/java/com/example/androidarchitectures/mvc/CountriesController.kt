package com.example.androidarchitectures.mvc

import com.example.androidarchitectures.model.CountriesService
import com.example.androidarchitectures.model.Country
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CountriesController(view:MVCActivity) {
    private lateinit var mvcActivityView: MVCActivity
    private lateinit var countriesService:CountriesService

    init {
        mvcActivityView = view
        countriesService = CountriesService()
        fetchCountries()
    }


    private fun fetchCountries() {
        countriesService.getCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableSingleObserver<List<Country>>(){
                override fun onSuccess(value: List<Country>?) {
                    var countryList = ArrayList<String>()
                    if (value != null) {
                        for(item in value){
                          countryList.add(item.countryName)
                        }
                    }
                    mvcActivityView.setValues(countryList)

                }

                override fun onError(e: Throwable?) {
                    mvcActivityView.onError()
                }
            })
    }

    fun onRefresh() {
        fetchCountries()
    }

}