package mx.madg.easyfit.Models.Adaptadores

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.Models.Contacto.Contacto
import mx.madg.easyfit.Models.Listeners.ContactosListener
import mx.madg.easyfit.Models.RutinaDay
import mx.madg.easyfit.R

// TODO: Add type of array
class ContactosAdapter(val context: Context, var contactos: ArrayList<Contacto>) : RecyclerView.Adapter<ContactosAdapter.ContactosViewHolder>() {

    var listener: ContactosListener? = null

    inner class ContactosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentPosition: Int = -1
        private var currentContact: Contacto? = null

        private val txtNombre = itemView.findViewById<TextView>(R.id.tvNombreContacto)
        private val layout = itemView.findViewById<LinearLayout>(R.id.cLayout)

        fun setData(cliente: Contacto, position: Int) {
            txtNombre.text = cliente.nombre
            this.currentPosition = position
            this.currentContact = cliente
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactosViewHolder {
        Log.i("ContactosAdapter", "onCreateViewHolder: ViewHolder created")
        val itemView = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false)
        return ContactosViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactosViewHolder, position: Int) {
        Log.i("ContactosAdapter", "onBindViewHolder: position: $position")
        val contacto = contactos[position]
        holder.setData(contacto, position)

        val layout = holder.itemView.findViewById<LinearLayout>(R.id.cLayout) // TODO: Check layout id_name
        layout.setOnClickListener {
            listener?.clickDay(position)
        }
    }

    override fun getItemCount(): Int = contactos.size

}