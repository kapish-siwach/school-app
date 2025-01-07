package com.first.schoolapp.adaptor

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.first.schoolapp.R
import com.first.schoolapp.entity.Services

class ItemsAdaptor(private var items:List<Services>, private val onClick:(Services)-> Unit):RecyclerView.Adapter<ItemsAdaptor.ServiceViewHolder>() {
    inner class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val catName: TextView = view.findViewById(R.id.catName)
        val catImg: ImageView = view.findViewById(R.id.catImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.service_view, parent, false)
        return ServiceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val item = items[position]
        holder.catName.text = item.title
        holder.catImg.setImageResource(item.img)
        val background = holder.itemView.background
        if (background is GradientDrawable) {
            val randomColor = getRandomColor()
            background.setColor(randomColor)
        }

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    private fun getRandomColor(): Int {
        val colors = listOf(
            Color.parseColor("#4CAF50"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#FFC107"),
            Color.parseColor("#607D8B"),
            Color.parseColor("#00BCD4"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#795548"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#E91E63")
        )
        return colors.random()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<Services>) {
        items = newItems
        notifyDataSetChanged()
    }
}