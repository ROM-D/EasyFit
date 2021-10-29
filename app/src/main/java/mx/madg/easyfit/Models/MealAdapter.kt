package mx.madg.easyfit.Models

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import mx.madg.easyfit.R

class MealAdapter(val context: Context, val meals: ArrayList<DietaMeal>) : RecyclerView.Adapter<MealAdapter.DietaMealViewHolder>() {

    var listener: DietDayListener? = null

    inner class DietaMealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentPosition: Int = -1
        private var currentMeal: DietaMeal? = null

        // TODO: Initialize items in Screen
        private val txtDietDay = itemView.findViewById<TextView>(R.id.tvDay)
        private val txtMealHour = itemView.findViewById<TextView>(R.id.tvDate) // TODO: Implement time schedule
        private val layout = itemView.findViewById<LinearLayout>(R.id.cLayout)

        fun setData(meal: DietaMeal, position: Int) {
            // TODO: Assign value to items in Screen
            txtDietDay.text = meal.meal
            txtMealHour.text = "HH:MM:SS"  // TODO: Implement time schedule
            this.currentPosition = position
            this.currentMeal = meal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietaMealViewHolder {
        Log.i("MealAdapter", "onCreateViewHolder: ViewHolder created")
        val itemView = LayoutInflater.from(context).inflate(R.layout.diet_item, parent, false)
        return DietaMealViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DietaMealViewHolder, position: Int) {
        Log.i("MealAdapter", "onBindViewHolder: position: $position")
        val meal = meals[position]
        holder.setData(meal, position)

        val layout = holder.itemView.findViewById<LinearLayout>(R.id.cLayout)
        layout.setOnClickListener {
            listener?.clickMeal(position)
        }
    }

    override fun getItemCount(): Int = meals.size
}