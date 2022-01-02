package com.example.androidarchitectures.mvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.androidarchitectures.R
import kotlinx.android.synthetic.main.activity_mvcactivity.*

class MVPActivity : AppCompatActivity(), CountriesPresenter.View {

    private var listValues: MutableList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var countriesPresenter: CountriesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvpactivity)
        countriesPresenter = CountriesPresenter(this)
        title = "MVP Activity"
        adapter = ArrayAdapter(this@MVPActivity, R.layout.row_layout, R.id.listText, listValues)
        list.adapter = adapter
        list.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "You click on ${listValues.get(position)}", Toast.LENGTH_SHORT).show()
        }
    }

    fun onRetry(view: View) {
        countriesPresenter.onRefresh()
        retryButton.visibility = View.GONE
        list.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }

    override fun setValues(countries: List<String>) {
        listValues.clear()
        listValues.addAll(countries)
        retryButton.visibility = View.GONE
        list.visibility = View.VISIBLE
        progress.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }

    override fun onError() {
        Toast.makeText(this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show()
        retryButton.visibility = View.VISIBLE
        list.visibility = View.GONE
        progress.visibility = View.GONE
    }
}