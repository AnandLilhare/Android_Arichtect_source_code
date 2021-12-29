package com.example.androidarchitectures

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.androidarchitectures.mvc.MVCActivity
import com.example.androidarchitectures.mvp.MVPActivity
import com.example.androidarchitectures.mvvm.MVVMActivity

class ArchitecturesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

   fun onMVC(view : View) {
       startActivity(Intent(ArchitecturesActivity@this, MVCActivity::class.java))
   }

   fun onMVP(view : View) {
     startActivity(Intent(ArchitecturesActivity@this, MVPActivity::class.java))
   }

   fun onMVVM(view: View) {
     startActivity(Intent(MVVMActivity@this, MVVMActivity::class.java))
   }

}