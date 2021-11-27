package mx.madg.easyfit.ui.Contactos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.madg.easyfit.MainActivity
import mx.madg.easyfit.Models.Adaptadores.ContactosAdapter
import mx.madg.easyfit.Models.Adaptadores.RutinaDayAdapter
import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.Models.Contacto.Contacto
import mx.madg.easyfit.Models.Dieta.Opcion
import mx.madg.easyfit.Models.Listeners.ContactosListener
import mx.madg.easyfit.Models.MyRutine
import mx.madg.easyfit.R
import mx.madg.easyfit.databinding.FragmentContactosBinding
import mx.madg.easyfit.ui.Rutina.Rutinas.NavRutinas
import mx.madg.easyfit.ui.Rutina.Rutinas.NavRutinasDirections
import mx.madg.easyfit.ui.Rutina.Rutinas.NavRutinasViewModel

class ContactosFragment : Fragment(), ContactosListener {

    companion object {
        fun newInstance() = ContactosFragment()
    }

    private lateinit var contactosViewModel: ContactosViewModel
    private var _binding: FragmentContactosBinding? = null
    private lateinit var contactosAdapter : ContactosAdapter
    private lateinit var baseDatos: FirebaseDatabase
    private var arrContactos: ArrayList<Contacto> = arrayListOf()
    private var loaded = 0;
    private lateinit var contactoNuevo: Contacto


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contactos, container, false)
        baseDatos = FirebaseDatabase.getInstance()
        setupRecyclerView(view)
        configurarEventos(view)
        return view
    }


    private fun configurarEventos(view: View?) {
        val btnIngresarId = view?.findViewById<EditText>(R.id.etNewId)

        val btnAgregarCliente = view?.findViewById<Button>(R.id.btnAgregarCliente)
        btnAgregarCliente?.setOnClickListener {
            println("Click!")
            println("SIZE: ${arrContactos.size}")
            agregarCliente(btnIngresarId?.text.toString(), arrContactos.size)
        }
    }

    private fun agregarCliente(id: String, position: Int) {
        val usuario = FirebaseAuth.getInstance().currentUser
        val referencia = baseDatos.getReference("Clientes/${id}/")
        val referenciaNutriologo = baseDatos.getReference("Nutriologos/${usuario?.uid}/clientes/${position}")
        val existing = baseDatos.getReference()
            .child("Clientes").child("${id}")

        if (existing != null) {
            // TODO: Get info from DB
            referencia.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val cliente = snapshot.getValue(Cliente::class.java)
                    val contacto = Contacto(id, cliente?.nombre!!, "55 35261726", cliente?.mail!!)
                    referenciaNutriologo.setValue(contacto)
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Error :(")
                }
            })
        }
    }


    private fun setupRecyclerView(view: View?) {
        val context = requireContext()

        // TODO: Obtener todos los clientes de un nutriologos
        val usuario = FirebaseAuth.getInstance().currentUser
        val referencia = baseDatos.getReference("Nutriologos/${usuario?.uid}/clientes/")

        // TODO: Get info from DB
        referencia.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (loaded != 1) {
                    for (contacto in snapshot.children) {
                        val cliente = contacto.getValue(Contacto::class.java)
                        arrContactos.add(cliente!!)
                    }
                    contactosAdapter.notifyDataSetChanged()
                    loaded = 1
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        contactosAdapter = ContactosAdapter(context, arrContactos)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.contactos_recycler_view)
        recyclerView?.adapter = contactosAdapter
        recyclerView?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView?.layoutManager = layoutManager

        contactosAdapter.listener = this
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        contactosViewModel = ViewModelProvider(this).get(ContactosViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun clickDay(position: Int) {
        Log.i("Position", "Position: $position")
        val contacto = contactosAdapter.contactos[position]
        val accion = ContactosFragmentDirections.actionNavContactosToContactoFragment(contacto)
        findNavController().navigate(accion)
    }
}