package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room

class MainActivity2 : AppCompatActivity() {

    lateinit var db: contactDataBase
    lateinit var contactDao: contactDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        db = Room.databaseBuilder(
            applicationContext,
            contactDataBase::class.java, "contactdb"
        ).allowMainThreadQueries().build()
        contactDao = db.contactDao()
    }
}