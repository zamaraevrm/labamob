package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class UpdateCreateContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_create_contact_activity)

        val id = intent.getLongExtra(InfoAboutContactActivity.EXTRA_KEY, -1L)
        if( id == -1L){

        }
    }
}