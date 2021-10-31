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
import mx.madg.easyfit.Models.DietaOption
import mx.madg.easyfit.R

class OptionAdapter(val context: Context, var options: ArrayList<DietaOption>) : RecyclerView.Adapter<OptionAdapter.DietOptionViewHolder>() {

    var listener: DietDayListener? = null

    inner class DietOptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentPosition: Int = -1
        private var currentOption: DietaOption? = null

        // TODO: Call objects from Layout
        val txtOpcion = itemView.findViewById<TextView>(R.id.tvDiaDeLaSemana)

        fun setData(option: DietaOption, position: Int) {
            txtOpcion.text = option.opcion
            this.currentPosition = position
            this.currentOption = option
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietOptionViewHolder {
        Log.i("OptionAdapter", "onCreateViewHolder: ViewHolder created")
        val itemView = LayoutInflater.from(context).inflate(R.layout.block_item, parent, false)
        return DietOptionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DietOptionViewHolder, position: Int) {
        Log.i("OptionAdapter", "onBindViewHolder: position: $position")
        val option = options[position]
        holder.setData(option, position)

        val layout = holder.itemView.findViewById<LinearLayout>(R.id.cLayout)
        layout.setOnClickListener {
            listener?.clickOption(position)
        }
    }

    override fun getItemCount(): Int = options.size
}