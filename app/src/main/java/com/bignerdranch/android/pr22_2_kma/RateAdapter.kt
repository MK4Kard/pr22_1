package com.bignerdranch.android.pr22_2_kma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RateAdapter: RecyclerView.Adapter<RateAdapter.RateViewHolder>() {
    var data:List<RateEntity> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    lateinit var selectedRate: RateEntity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateAdapter.RateViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rate_item, parent, false)
        return RateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.s_n.text = data[position].name_start
        holder.s_d.text = data[position].data_start.toString()
        holder.o_n.text = data[position].name_one
        holder.o_d.text = data[position].data_one.toString()
        holder.t_n.text = data[position].name_two
        holder.t_d.text = data[position].data_two.toString()

        holder.itemView.setOnClickListener{
            selectedRate = data[position]
        }
    }

    override fun getItemCount(): Int = data.size

    class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val s_n: TextView = itemView.findViewById(R.id.start_n)
        val s_d: TextView = itemView.findViewById(R.id.start_d)
        val o_n: TextView = itemView.findViewById(R.id.one_n)
        val o_d: TextView = itemView.findViewById(R.id.one_d)
        val t_n: TextView = itemView.findViewById(R.id.two_n)
        val t_d: TextView = itemView.findViewById(R.id.two_d)
    }
}