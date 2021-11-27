package mx.madg.easyfit.Models.Adaptadores

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.madg.easyfit.Models.Listeners.DietDayListener
import mx.madg.easyfit.Models.DietaDay
import mx.madg.easyfit.R
import java.util.*
import kotlin.collections.ArrayList

class DayAdapter(val context: Context, var days: ArrayList<DietaDay>) : RecyclerView.Adapter<DayAdapter.DietDayViewHolder>() {

    var listener: DietDayListener? = null

    inner class DietDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentPosition: Int = -1
        private var currentDay: DietaDay? = null
        val calendar: Calendar = Calendar.getInstance()
        val c = calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val theDay = calendar.get(Calendar.DAY_OF_WEEK)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        val weekYear = calendar.get(Calendar.WEEK_OF_YEAR)
        val week = calendar.get(Calendar.WEEK_OF_YEAR)
        val dM = calendar.get(Calendar.DAY_OF_MONTH) + 2

        val first = calendar.firstDayOfWeek

        private val txtDietDay = itemView.findViewById<TextView>(R.id.tvNombreContacto)
        private val txtDate = itemView.findViewById<TextView>(R.id.tvDate)
        // private val imvDone = itemView.findViewById<ImageView>(R.id.imv_favorites)
        private val layout = itemView.findViewById<LinearLayout>(R.id.cLayout) // change to LL

        // TODO: New layout

        fun setData(day: DietaDay, position: Int) {
            txtDietDay.text = day.day

            // txtDate.text = first.toString() + dM.toString() + "/" + month.toString() + "/" + year.toString()
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

        val layout = holder.itemView.findViewById<LinearLayout>(R.id.cLayout)
        layout.setOnClickListener {
            listener?.clickDay(position)
        }
        // holder.setListeners(position)
    }

    override fun getItemCount(): Int = days.size

}