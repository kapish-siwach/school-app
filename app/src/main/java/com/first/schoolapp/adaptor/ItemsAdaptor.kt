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
import kotlin.math.absoluteValue

import androidx.core.content.ContextCompat

class ItemsAdaptor(
    private var items: List<Services>,
    private val onClick: (Services) -> Unit
) : RecyclerView.Adapter<ItemsAdaptor.ServiceViewHolder>() {

    private val colors = listOf(
        R.color.colorDarkGreen,
        R.color.colorDarkBlue,
        R.color.colorDarkRed,
        R.color.colorDarkPurple,
        R.color.colorDarkYellow,
        R.color.colorDarkGreyBlue,
        R.color.colorDarkCyan,
        R.color.colorDarkOrange,
        R.color.colorDarkBrown,
        R.color.colorDarkIndigo,
        R.color.colorDarkPink
    )

    private var colorIndex = 0

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

        // Assign sequential colors
        val colorRes = colors[colorIndex]
        colorIndex = (colorIndex + 1) % colors.size // Increment and reset after the last color
        val color = ContextCompat.getColor(holder.itemView.context, colorRes)

        // Set background color
        val background = holder.itemView.background
        if (background is GradientDrawable) {
            background.setColor(color)
        }

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<Services>) {
        items = newItems
        notifyDataSetChanged()
    }
}
