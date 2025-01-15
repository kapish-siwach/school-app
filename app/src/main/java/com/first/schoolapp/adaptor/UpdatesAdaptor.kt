package com.first.schoolapp.adaptor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.first.schoolapp.R
import com.first.schoolapp.entity.UpdateData
import com.first.schoolapp.viewmodel.UpdatesViewModel

class UpdatesAdaptor(private var updates: List<UpdateData>, private val viewModel: UpdatesViewModel) : RecyclerView.Adapter<UpdatesAdaptor.UpdateViewHolder>() {

    inner class UpdateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val updateTitle: TextView = view.findViewById(R.id.update)
        val deleteUpdate: ImageView = view.findViewById(R.id.dltUpdate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.update_view, parent, false)
        return UpdateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return updates.size
    }

    override fun onBindViewHolder(holder: UpdateViewHolder, position: Int) {
        val updateData = updates[position]
        holder.updateTitle.text = updateData.update
        holder.deleteUpdate.setOnClickListener {
            viewModel.deleteUpdate(updateData.id)
            Toast.makeText(holder.itemView.context, "Deleted successfully", Toast.LENGTH_SHORT).show()
        }

    }

    // Method to update the data in the RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newUpdates: List<UpdateData>) {
        updates = newUpdates
        notifyDataSetChanged()
    }
}
