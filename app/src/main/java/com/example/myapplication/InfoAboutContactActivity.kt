package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoAboutContactActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEY_Info = "EXTRA"
    }

    private val contactDatabase by lazy {
        ContactDatabase.getDatabase(this).contactDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_about_contact_activity)

        val id = intent.getLongExtra(MainActivity.EXTRA_KEY,0L)
        var contact = ContactEntity()
        lifecycleScope.launch(Dispatchers.IO){
             contact = contactDatabase.getById(id)
        }

        val textViewFirstname = findViewById<TextView>(R.id.textViewFirstname)
        textViewFirstname.text = contact.firstname

        val textViewSurname = findViewById<TextView>(R.id.textViewSurname)
        textViewSurname.text = contact.surname

        val textViewBirthday = findViewById<TextView>(R.id.textViewbirthday)
        textViewBirthday.text = contact.birthday.toString()

        val textViewPhone = findViewById<TextView>(R.id.textViewPhone)
        textViewPhone.text = contact.phone

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonModify = findViewById<Button>(R.id.buttonModify)
        buttonModify.setOnClickListener {
            val intent = Intent(this, UpdateCreateContactActivity::class.java)
            intent.putExtra(InfoAboutContactActivity.EXTRA_KEY_Info, contact.id)
            startActivity(intent)
        }

        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            lifecycleScope.launch {
                contactDatabase.delete(contact)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}