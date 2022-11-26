package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.icu.text.SimpleDateFormat
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

    var _contact:ContactEntity = ContactEntity()

    fun Date.toSimpleString() : String {
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(this)
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

        val id = intent.getLongExtra(InfoAboutContactActivity.EXTRA_KEY_Info, -1L)
        if( id != -1L){
            lifecycleScope.launch(Dispatchers.IO) {
                _contact = contactDatabase.getById(id)
            }

            editTextFirstname.text = Editable.Factory.getInstance().newEditable(_contact.firstname)
            editTextSurname.text = Editable.Factory.getInstance().newEditable(_contact.surname)
            editTextDate.text = Editable.Factory.getInstance().newEditable(_contact.birthday?.toSimpleString().toString())
            editTextPhone.text = Editable.Factory.getInstance().newEditable(_contact.phone)
        }


        editTextFirstname.doAfterTextChanged {
            _contact.firstname = it.toString()
        }

        editTextSurname.doAfterTextChanged {
            _contact.surname = it.toString()
        }

        editTextDate.setKeyListener(null)

        editTextPhone.doAfterTextChanged {
            _contact.phone = it.toString()
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
            _contact.birthday = Date(year - 1900, month, day)
        }, year, month, day)

        editTextDate.setOnClickListener {
            dpd.show()
        }

        val buttonAdd = findViewById<Button>(R.id.buttonDelete)
        buttonAdd.setOnClickListener {
           dialogYesOrNo(
                   this,
                   "Вопрос",
                   "Сохранить?",
                   DialogInterface.OnClickListener { dialog, d ->

                       lifecycleScope.launch(Dispatchers.IO) {
                           if(id != -1L){
                               contactDatabase.update(_contact)
                           }else{
                               contactDatabase.insert(_contact)
                           }
                       }

                       finish()
                   }
            )

        }

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }

    }



}

