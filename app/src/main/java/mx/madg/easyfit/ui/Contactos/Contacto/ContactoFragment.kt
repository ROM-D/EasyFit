package mx.madg.easyfit.ui.Contactos.Contacto

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import mx.madg.easyfit.ActivityNav
import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.Models.Dieta.Dia
import mx.madg.easyfit.R
import mx.madg.easyfit.ui.Contactos.ContactosFragmentDirections
import mx.madg.easyfit.ui.Dieta.Opcion.OpcionFragmentArgs

class ContactoFragment : Fragment() {

    companion object {
        fun newInstance() = ContactoFragment()
    }

    private lateinit var viewModel: ContactoViewModel
    private lateinit var baseDatos: FirebaseDatabase
    private lateinit var referencia: DatabaseReference
    private lateinit var cliente: Cliente
    private lateinit var dietas: ArrayList<Dia>  // TODO: Retrieve dieta from user
    // TODO: Referenciar datos obtenidos del fragmento anterior
    private val args : ContactoFragmentArgs by navArgs<ContactoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.contacto_fragment, container, false)
        baseDatos = FirebaseDatabase.getInstance()
        leerDatos()
        obtenerCliente(view)
        configurarEventos(view)
        return view
    }

    private fun obtenerCliente(view: View?) {
        referencia = baseDatos.getReference("Clientes/${args.contactoCliente.id}/")
        referencia.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cliente = snapshot.getValue(Cliente::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error :'(")
            }
        })
    }

    override fun onStart() {
        super.onStart()

    }

    private fun configurarEventos(view: View?) {
        val btnAsignarDieta = view?.findViewById<Button>(R.id.btnAsignarDieta)!!
        btnAsignarDieta.setOnClickListener {
            val accion = ContactoFragmentDirections.actionContactoFragmentToDietaVistaNutriologoFragment(cliente)
            findNavController().navigate(accion)
        }

        val btnActualizarPerfil = view?.findViewById<Button>(R.id.btnActualizarPerfil)!!
        btnActualizarPerfil.setOnClickListener {
            actualizarDatos()
        }
    }


    private fun actualizarDatos() {
        // TODO: Check Diet overwritting :0
        val referencia = baseDatos.getReference("Clientes/${args.contactoCliente.id}/")
        val datosActualizados = Cliente(
            args.contactoCliente.id,
            view?.findViewById<TextView>(R.id.tvNombre)?.text.toString(),
            view?.findViewById<TextView>(R.id.tvEmail)?.text.toString(),
            dietas,
            view?.findViewById<TextView>(R.id.tvPeso)?.text.toString().toDouble(),
            view?.findViewById<TextView>(R.id.tvAltura)?.text.toString().toDouble(),
            view?.findViewById<TextView>(R.id.tvSexo)?.text.toString(),
            view?.findViewById<TextView>(R.id.tvPorcentajeMusculo)?.text.toString().toDouble(),
            view?.findViewById<TextView>(R.id.tvPorcentajeGrasa)?.text.toString().toDouble(),
            0,
        )
        referencia.setValue(datosActualizados)
    }


    private fun leerDatos() {
        val usuario = FirebaseAuth.getInstance().currentUser
        val referencia = baseDatos.getReference("Clientes/${args.contactoCliente.id}/")

        referencia.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val cliente = snapshot.getValue(Cliente::class.java)

                dietas = cliente?.dieta!!  // Check functionality

                val nombre = view?.findViewById<TextView>(R.id.tvNombre)
                val id = view?.findViewById<TextView>(R.id.tvId)
                val email = view?.findViewById<TextView>(R.id.tvEmail)
                val peso = view?.findViewById<TextView>(R.id.tvPeso)
                val altura = view?.findViewById<TextView>(R.id.tvAltura)
                val sexo = view?.findViewById<TextView>(R.id.tvSexo)
                val porcentajeGrasa = view?.findViewById<TextView>(R.id.tvPorcentajeGrasa)
                val porcentajeMusculo = view?.findViewById<TextView>(R.id.tvPorcentajeMusculo)
                val imagen = view?.findViewById<ImageView>(R.id.imageProfile)

                nombre?.text = cliente?.nombre.toString()
                id?.text = "id: " + cliente?.id.toString()
                email?.text = cliente?.mail.toString()
                peso?.text = cliente?.peso.toString()
                altura?.text = cliente?.altura.toString()
                sexo?.text = cliente?.sexo.toString()
                porcentajeGrasa?.text = cliente?.porcentajeGrasaCorporal.toString()
                porcentajeMusculo?.text = cliente?.porcentajeDeMusculo.toString()
                imagen?.setImageURI(usuario?.photoUrl)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error :(")
            }

        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}