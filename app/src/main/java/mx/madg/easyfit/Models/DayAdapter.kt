package mx.madg.easyfit.Models

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import mx.madg.easyfit.R

class DayAdapter(val context: Context, var days: ArrayList<DietaDay>) : RecyclerView.Adapter<DayAdapter.DietDayViewHolder>() {

    var listener: DietDayListener? = null

    inner class DietDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentPosition: Int = -1
        private var currentDay: DietaDay? = null

        private val txtDietDay = itemView.findViewById<TextView>(R.id.tvDay)
        // private val imvDone = itemView.findViewById<ImageView>(R.id.imv_favorites)
        private val layout = itemView.findViewById<ConstraintLayout>(R.id.cLayout) // change to LL

        fun setData(day: DietaDay, position: Int) {
            txtDietDay.text = day.day

            //if (day.isCompleted)
              //  imvDone.setImageDrawable()
            //else
              //  imvDone.setImageDrawable()

            this.currentPosition = position
            this.currentDay = day
        }

        fun setListeners(position: Int) {
            layout.setOnClickListener {
                listener?.clickDay(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietDayViewHolder {
        Log.i("DayAdapter", "onCreateViewHolder: ViewHolder created")
        val itemView = LayoutInflater.from(context).inflate(R.layout.diet_item, parent, false)
        return DietDayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DietDayViewHolder, position: Int) {
        Log.i("DayAdapter", "onBindViewHolder: position: $position")
        val day = days[position]
        holder.setData(day, position)

        val layout = holder.itemView.findViewById<ConstraintLayout>(R.id.cLayout)
        layout.setOnClickListener {
            listener?.clickDay(position)
        }
        // holder.setListeners(position)
    }

    override fun getItemCount(): Int = days.size

}