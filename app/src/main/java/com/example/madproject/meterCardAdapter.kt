package com.example.madproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class meterCardAdapter(mcontext:Context): RecyclerView.Adapter<meterCardAdapter.adapterView>() {
    val Cont=mcontext

    var data: ArrayList<Meters> = ArrayList<Meters>()

    class adapterView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val meterNo: TextView = itemView.findViewById(R.id.meter_1)
        val address: TextView = itemView.findViewById(R.id.address)
        val card: CardView = itemView.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterView {
        val iv = LayoutInflater.from(parent.context).inflate(R.layout.meter_cards,parent,false)
        return adapterView(iv)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setList(list: ArrayList<Meters>){
        data = list
    }

    override fun onBindViewHolder(holder: adapterView, position: Int) {
        holder.meterNo.text = data.get(position).mNo.toString()
        holder.address.text = data.get(position).street.toString()+", "+data.get(position).city.toString()
        holder.card.setOnClickListener(View.OnClickListener {
//                println("Jeev Vaazhgaaaa!!!")
                val cardRedirect : Intent = Intent(Cont, smartMeterView::class.java)
                cardRedirect.putExtra("mNo", data.get(position).mNo.toString())
                Cont.startActivity(cardRedirect)
            })
        }
    }
