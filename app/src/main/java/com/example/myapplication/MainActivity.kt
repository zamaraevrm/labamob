package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val contactsList = mutableListOf<ContactEntity>()
    private lateinit var adapter: RecyclerAdapter

    private val contactDatabase by lazy {
        ContactDatabase.getDatabase(this).contactDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val job = lifecycleScope.launch(Dispatchers.IO){
            contactsList.addAll(contactDatabase.getAllContacts)
        }
        lifecycleScope.launch {
            job.cancelAndJoin()
        }

        adapter = RecyclerAdapter(contactsList) {
            val intent = Intent(this, InfoAboutContactActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val buttonAddContact = findViewById<Button>(R.id.buttonAddContact)
        buttonAddContact.setOnClickListener{
//            val intent = Intent(this, UpdateCreateContactActivity::class.java)
//            startActivity(intent)
        }

        val editTextSearch = findViewById<EditText>(R.id.editTextTextPersonName)


    }
}