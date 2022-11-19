package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class InfoAboutContactActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEY = "EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_about_contact_activity)




    }
}