package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val contactsList = mutableListOf<ContactEntity>()
    private lateinit var adapter: RecyclerAdapter

    private val contactDatabase by lazy {
        ContactDatabase.getDatabase(this).contactDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactsList.addAll(contactDatabase.allContacts)

        adapter = RecyclerAdapter(contactsList) {
            val intent = Intent(this, UpdaetCreateContactActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val buttonAddContact = findViewById<Button>(R.id.buttonAddContact)
        buttonAddContact.setOnClickListener{
            val intent = Intent(this, InfoAboutContactActivity::class.java)
            startActivity(intent)
        }

        val editTextSearch = findViewById<EditText>(R.id.editTextTextPersonName)

    }
}