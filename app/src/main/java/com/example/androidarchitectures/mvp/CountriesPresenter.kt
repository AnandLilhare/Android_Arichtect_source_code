package com.example.androidarchitectures.mvp

import com.example.androidarchitectures.model.CountriesService
import com.example.androidarchitectures.model.Country
import com.example.androidarchitectures.mvc.MVCActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CountriesPresenter(view : View) {
    private lateinit var activityView: View
    private lateinit var countriesService: CountriesService

    init {
        activityView= view
        countriesService = CountriesService()
        fetchCountries()
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
                    activityView.setValues(countryList)

                }

                override fun onError(e: Throwable?) {
                    activityView.onError()
                }
            })
    }

    fun onRefresh() {
        fetchCountries()
    }

    interface View {
        fun setValues(countries : List<String>)
        fun onError()
    }
}