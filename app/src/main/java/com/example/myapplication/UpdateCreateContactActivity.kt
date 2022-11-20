package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import java.util.*

class UpdateCreateContactActivity : AppCompatActivity() {

    private val contactDatabase by lazy {
        ContactDatabase.getDatabase(this).contactDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_create_contact_activity)

        var contact = ContactEntity()

        val id = intent.getLongExtra(InfoAboutContactActivity.EXTRA_KEY, -1L)
        if( id != -1L){
            lifecycleScope.launch(Dispatchers.IO) {
                contact = contactDatabase.getById(id)
            }
        }

        val editTextFirstname = findViewById<EditText>(R.id.editTextFirstname)
        editTextFirstname.doAfterTextChanged {
            contact.firstname = it.toString()
        }

        val editTextSurname = findViewById<EditText>(R.id.editTextSurname)
        editTextSurname.doAfterTextChanged {
            contact.surname = it.toString()
        }

        val editTextDate = findViewById<EditText>(R.id.editTextDate)
        editTextDate.setKeyListener(null);
//        editTextDate.doAfterTextChanged {
//            contact.birthday = Date(it.toString())
//        }


        val editTextPhone = findViewById<EditText>(R.id.editTextPhone)
        editTextPhone.doAfterTextChanged {
            contact.phone = it.toString()
        }

        //val textView = findViewById<View>(R.id.textView) as TextView
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            // Display Selected date in textbox
            //textView.text = "You Selected: $day/$month/$year"
            editTextDate.text = Editable.Factory.getInstance().newEditable("$day/$month/$year")

        }, year, month, day)

        editTextDate.setOnClickListener {
            dpd.show()
        }

        val job = lifecycleScope.launch(Dispatchers.IO){
            if(id != -1L){
                contactDatabase.update(contact)
            }else{
                contactDatabase.insert(contact)
            }
        }

        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            lifecycleScope.launch{
                job.cancelAndJoin()
            }
        }

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}