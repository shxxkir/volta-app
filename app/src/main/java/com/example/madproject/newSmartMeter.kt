package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class newSmartMeter : AppCompatActivity() {

    lateinit var meterNo : EditText
    lateinit var elecNo : EditText
    lateinit var street : EditText
    lateinit var city : EditText
    lateinit var submit : Button
    lateinit var cancel : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_smart_meter)
        meterNo = findViewById(R.id.meterNo_form)
        elecNo = findViewById(R.id.elecAccNo)
        street = findViewById(R.id.street_form)
        city = findViewById(R.id.city)
        submit = findViewById(R.id.submit)

        cancel = findViewById(R.id.cancel)
        cancel.setOnClickListener(View.OnClickListener {
            val cancelRedirect : Intent = Intent(this@newSmartMeter, smartMeter::class.java)
            startActivity(cancelRedirect)
        })

        val key = intent.getStringExtra("Meter Number")
        if (key != null) {
            val meterNo1: Int = key.toInt()
            val elecNumber: Int = intent.getStringExtra("Electricity Number").toString().toInt()
            val street1: String = intent.getStringExtra("Street").toString()
            val city1: String = intent.getStringExtra("City").toString()
            meterNo.setText(meterNo1.toString())
            elecNo.setText(elecNumber.toString())
            street.setText(street1)
            city.setText(city1)
        }

        submit.setOnClickListener(View.OnClickListener {
            val mNo : Int = meterNo.text.toString().toInt()
            val eNo : Int = elecNo.text.toString().toInt()
            val street : String = street.text.toString()
            val city : String = city.text.toString()
            var id : Int = mNo

            //Write a message to the database
            val database = Firebase.database
            val myRef = database.getReference("meter")
            val mNum = myRef.child(id.toString())
            var meterNum = mNum.child("Meter Number")
            meterNum.setValue(mNo)
            mNum.child("Electricity Number").setValue(eNo)
            mNum.child("Street").setValue(street)
            mNum.child("City").setValue(city)

            Toast.makeText(this, "Meter Details Added Successfully", Toast.LENGTH_SHORT).show()

            val mainUIRedirect : Intent = Intent(this@newSmartMeter, smartMeter::class.java)
            startActivity(mainUIRedirect)
        })
    }
}
