package mx.madg.easyfit.Models.Adaptadores

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.madg.easyfit.Models.RutinaDay
import mx.madg.easyfit.Models.Listeners.RutinaDayListener
import mx.madg.easyfit.R

class RutinaDayAdapter(val context: Context, var days: ArrayList<RutinaDay>) : RecyclerView.Adapter<RutinaDayAdapter.RutinaDayViewHolder>() {

    var listener: RutinaDayListener? = null

    inner class RutinaDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentPosition: Int = -1
        private var currentDay: RutinaDay? = null

        private val txtDietDay = itemView.findViewById<TextView>(R.id.tvrDiaDeLaSemana)
        private val layout = itemView.findViewById<LinearLayout>(R.id.crLayout)

        fun setData(day: RutinaDay, position: Int) {
            txtDietDay.text = day.day
            this.currentPosition = position
            this.currentDay = day
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaDayViewHolder {
        Log.i("RutinaDayAdapter", "onCreateViewHolder: ViewHolder created")
        val itemView = LayoutInflater.from(context).inflate(R.layout.rutina_item, parent, false)
        return RutinaDayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RutinaDayViewHolder, position: Int) {
        Log.i("RutinaDayAdapter", "onBindViewHolder: position: $position")
        val day = days[position]
        holder.setData(day, position)

        val layout = holder.itemView.findViewById<LinearLayout>(R.id.crLayout)
        layout.setOnClickListener {
            listener?.clickDay(position)
        }
    }

    override fun getItemCount(): Int = days.size
}