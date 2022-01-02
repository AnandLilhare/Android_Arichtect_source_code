package com.example.androidarchitectures.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_mvcactivity.*
import androidx.lifecycle.ViewModelProviders
class MVVMActivity : AppCompatActivity() {
    private var listValues: MutableList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var viewModel: CountriesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.androidarchitectures.R.layout.activity_mvvmactivity)
        title = "MVVM Activity"
        viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)
        adapter = ArrayAdapter(this@MVVMActivity, com.example.androidarchitectures.R.layout.row_layout, com.example.androidarchitectures.R.id.listText, listValues)
        list.adapter = adapter
        list.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "You click on ${listValues.get(position)}", Toast.LENGTH_SHORT).show()
        }
        observeViewModel();
    }
    private fun observeViewModel() {
        viewModel.getCountries().observe(this,
            { countries: List<String> ->
                if (countries != null) {
                    listValues.clear()
                    listValues.addAll(countries)
                    list.visibility = View.VISIBLE
                    adapter.notifyDataSetChanged()
                } else {
                    list.visibility = View.GONE
                }
            })
        viewModel.getCountryError().observe(this, { error: Boolean ->
            progress.visibility = View.GONE
            if (error) {
                Toast.makeText(this, getString(com.example.androidarchitectures.R.string.error_msg), Toast.LENGTH_SHORT).show()
                retryButton.visibility = View.VISIBLE
            } else {
                retryButton.visibility = View.GONE
            }
        })
    }
    fun onRetry(view: View?) {
        viewModel.onRefresh()
        list.visibility = View.GONE
        retryButton.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }
}