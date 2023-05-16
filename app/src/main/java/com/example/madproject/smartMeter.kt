package com.example.madproject

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build.VERSION_CODES.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class smartMeter : AppCompatActivity() {
    lateinit var newMeter : ConstraintLayout
    lateinit var card : RelativeLayout
    lateinit var view : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.madproject.R.layout.activity_smart_meter)
        newMeter = findViewById(com.example.madproject.R.id.newMeter)
        newMeter.setOnClickListener(View.OnClickListener {
            val formRedirect : Intent = Intent(this@smartMeter, newSmartMeter::class.java)
            startActivity(formRedirect)
        })

//        card1 = findViewById(R.id.meter1)
//        card1.setOnClickListener(View.OnClickListener {
//            val viewMeter : Intent = Intent(this@smartMeter, smartMeterView::class.java)
//            startActivity(viewMeter)
//        })

        val database = Firebase.database
        val myRef = database.getReference("meter")

        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.value
                val data = value as HashMap<String,Any>
                val data4: ArrayList<Meters> = ArrayList<Meters>()

                for(i in data.keys){
                    val data2 = data.get(i)
                    val data3 = data2 as HashMap<String,Any>
                    val meter: Meters = Meters()
                    meter.mNo = data2.get("Meter Number").toString().toInt()
                    meter.street = data2.get("Street").toString()
                    meter.city = data2.get("City").toString()
                    data4.add(meter)
                }
                val adapt:meterCardAdapter = meterCardAdapter(this@smartMeter)
                adapt.setList(data4)
                view = findViewById(com.example.madproject.R.id.recyclerView)
                view.adapter=adapt
                view.layoutManager = LinearLayoutManager(this@smartMeter)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }

        })

    }
}