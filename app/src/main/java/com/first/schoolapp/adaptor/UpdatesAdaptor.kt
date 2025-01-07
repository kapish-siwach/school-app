package com.first.schoolapp.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.first.schoolapp.R

class UpdatesAdaptor(private var updates:List<String>):RecyclerView.Adapter<UpdatesAdaptor.UpdateViewHolder>() {

    inner class UpdateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val update: TextView = view.findViewById(R.id.update)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.update_view,parent,false)
        return UpdateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return updates.size
    }

    override fun onBindViewHolder(holder: UpdateViewHolder, position: Int) {
        holder.update.text=updates[position]
    }
    fun updateData(newUpdates: List<String>) {
        updates = newUpdates
        notifyDataSetChanged()
    }

}