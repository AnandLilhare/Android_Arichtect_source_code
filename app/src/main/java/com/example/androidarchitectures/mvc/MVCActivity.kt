package com.example.androidarchitectures.mvc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.androidarchitectures.R
import kotlinx.android.synthetic.main.activity_mvcactivity.*

class MVCActivity : AppCompatActivity() {
    private var listValues: MutableList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var countriesController: CountriesController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvcactivity)
        countriesController = CountriesController(this)
        title = "MVC Activity"
        adapter = ArrayAdapter(this@MVCActivity, R.layout.row_layout, R.id.listText, listValues)
        list.adapter = adapter
        list.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "You click on ${listValues.get(position)}", Toast.LENGTH_SHORT).show()
        }

    }
    fun setValues(values : List<String>){
        listValues.clear()
        listValues.addAll(values)
        retryButton.visibility = View.GONE
        list.visibility = View.VISIBLE
        progress.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }

    fun onError() {
        Toast.makeText(this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show()
        retryButton.visibility = View.VISIBLE
        list.visibility = View.GONE
        progress.visibility = View.GONE
    }

    fun onRetry(view:View) {
      countriesController.onRefresh()
        retryButton.visibility = View.GONE
        list.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }

}