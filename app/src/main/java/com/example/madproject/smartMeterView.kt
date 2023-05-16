package com.example.madproject

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class smartMeterView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_meter_view)

        val meterNo: TextView = findViewById(R.id.meterNo_data)
        val elecNo: TextView = findViewById(R.id.elec_data)
        val street: TextView = findViewById(R.id.street_data)
        val city: TextView = findViewById(R.id.city_data)

        val update: Button = findViewById(R.id.update)
        val delete: Button = findViewById(R.id.delete)

        val id = intent.getStringExtra("mNo").toString()

        val database = Firebase.database
        val myRef = database.getReference("meter").child(id)

        delete.setOnClickListener(View.OnClickListener {
            myRef.removeValue()
                .addOnSuccessListener(
                    OnSuccessListener{
                        println("Deletion Success")
                })
                .addOnFailureListener(
                OnFailureListener {
                    println("Deletion Failure")
                }
            )
        })

        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.value
                val data = value as HashMap<String,Any>
                meterNo.text = data.get("Meter Number").toString()
                elecNo.text = data.get("Electricity Number").toString()
                street.text = data.get("Street").toString()
                city.text = data.get("City").toString()
                //Log.d(ContentValues.TAG, "Value is: " + value.toString())

                update.setOnClickListener(View.OnClickListener {
                    val updateDirect : Intent = Intent(this@smartMeterView, newSmartMeter::class.java)
                    updateDirect.putExtra("Meter Number",data.get("Meter Number").toString())
                    updateDirect.putExtra("Electricity Number",data.get("Electricity Number").toString())
                    updateDirect.putExtra("Street",data.get("Street").toString())
                    updateDirect.putExtra("City",data.get("City").toString())
                    startActivity(updateDirect)
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }

        })
    }
}