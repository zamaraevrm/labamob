package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val contactsList = mutableListOf<ContactEntity>()
    //private lateinit var nonChagedContactList: List<ContactEntity>

    private lateinit var adapter: RecyclerAdapter

    companion object {
        const val EXTRA_KEY = "EXTRA"
    }

    private val contactDatabase by lazy {
        ContactDatabase.getDatabase(this).contactDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RecyclerAdapter(contactsList) {
            val intent = Intent(this, InfoAboutContactActivity::class.java)
            intent.putExtra(EXTRA_KEY, contactsList[it].id)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val buttonAddContact = findViewById<Button>(R.id.buttonAddContact)
        buttonAddContact.setOnClickListener{
           val intent = Intent(this, UpdateCreateContactActivity::class.java)
           startActivity(intent)
        }

        val editTextSearch = findViewById<EditText>(R.id.editTextTextPersonName)
        editTextSearch.doOnTextChanged { text, start, before, count ->

            lifecycleScope.launch(Dispatchers.IO) {
                val list = contactDatabase.getAllContacts.filter {
                        (it.firstname.contains(text.toString(), true)) ||
                        (it.surname?.contains(text.toString(), true) ?: false)}
                contactsList.clear()
                contactsList.addAll(list)
                withContext(Dispatchers.Main){
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch(Dispatchers.IO){
            contactsList.addAll(contactDatabase.getAllContacts)
            withContext(Dispatchers.Main){
                //val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                adapter?.notifyDataSetChanged()
            }
        }


    }

    override fun onStop() {
        super.onStop()

        contactsList.clear()
    }

}