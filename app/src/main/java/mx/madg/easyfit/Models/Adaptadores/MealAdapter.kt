package mx.madg.easyfit.Models.Adaptadores

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.madg.easyfit.Models.Dieta.Comida
import mx.madg.easyfit.Models.Listeners.DietDayListener
import mx.madg.easyfit.Models.DietaMeal
import mx.madg.easyfit.R

class MealAdapter(val context: Context, val meals: ArrayList<DietaMeal>, val id: Int) : RecyclerView.Adapter<MealAdapter.DietaMealViewHolder>() {

    var listener: DietDayListener? = null


    inner class DietaMealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentPosition: Int = -1
        private var currentMeal: DietaMeal? = null
        private var baseDatos: FirebaseDatabase = FirebaseDatabase.getInstance()

        // TODO: Call FB Data
        val usuario = FirebaseAuth.getInstance().currentUser


        // TODO: Initialize items in Screen
        private val txtDietDay = itemView.findViewById<TextView>(R.id.tvNombreContacto)
        private val txtMealHour = itemView.findViewById<TextView>(R.id.tvDate) // TODO: Implement time schedule
        private val layout = itemView.findViewById<LinearLayout>(R.id.cLayout)

        fun setData(meal: DietaMeal, position: Int) {
            val referencia = baseDatos.getReference("Clientes/${usuario?.uid}/dieta/${id}/comidas/${position}/")
            // TODO: Assign value to items in Screen
            txtDietDay.text = meal.meal
            // txtMealHour.text = "HH:MM:SS"  // TODO: Implement time schedule

            // TODO: Get info from DB
            referencia.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val comida = snapshot.getValue(Comida::class.java)
                    txtMealHour.text = comida?.horario  // TODO: Implement time schedule
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

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