package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
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

    fun dialogYesOrNo(
        activity: Activity,
        title: String,
        message: String,
        listener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
            listener.onClick(dialog, id)
        })
        builder.setNegativeButton("No", null)
        val alert = builder.create()
        alert.setTitle(title)
        alert.setMessage(message)
        alert.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_create_contact_activity)

        val editTextFirstname = findViewById<EditText>(R.id.editTextFirstname)
        val editTextSurname = findViewById<EditText>(R.id.editTextSurname)
        val editTextDate = findViewById<EditText>(R.id.editTextDate)
        val editTextPhone = findViewById<EditText>(R.id.editTextPhone)

        var contact = ContactEntity()

        val id = intent.getLongExtra(InfoAboutContactActivity.EXTRA_KEY_Info, -1L)
        if( id != -1L){
            lifecycleScope.launch(Dispatchers.IO) {
                contact = contactDatabase.getById(id)
            }

            editTextFirstname.text = Editable.Factory.getInstance().newEditable(contact.firstname)
            editTextSurname.text = Editable.Factory.getInstance().newEditable(contact.surname)
            editTextDate.text = Editable.Factory.getInstance().newEditable(contact.birthday?.year.toString() + "/" + contact.birthday?.month.toString() + "/" + contact.birthday?.day.toString())
            editTextPhone.text = Editable.Factory.getInstance().newEditable(contact.phone)
        }


        editTextFirstname.doAfterTextChanged {
            contact.firstname = it.toString()
        }


        editTextSurname.doAfterTextChanged {
            contact.surname = it.toString()
        }


        editTextDate.setKeyListener(null)
//        editTextDate.doAfterTextChanged {
//            contact.birthday = Date(it.toString())
//        }



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
            contact.birthday = Date(year, month, day)
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

        val buttonAdd = findViewById<Button>(R.id.buttonDelete)
        buttonAdd.setOnClickListener {
           dialogYesOrNo(
                   this,
                   "Вопрос",
                   "Вы перестали пить коньяк по утрам?",
                   DialogInterface.OnClickListener { dialog, id ->
                       // что делать, если нажали "да"
                        println("DA")
                       lifecycleScope.launch {
                           job.cancelAndJoin()
                       }
                       val intent = Intent(this, MainActivity::class.java)
                       startActivity(intent)
                   }
            )

        }

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }

    }



}

