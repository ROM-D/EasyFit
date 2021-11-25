package mx.madg.easyfit.ui.Dieta.Opcion

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.madg.easyfit.Models.Dieta.Opcion
import mx.madg.easyfit.R
import mx.madg.easyfit.ui.Dieta.OpcionesComida.OpcionesComidaFragmentArgs

class OpcionFragment : Fragment() {

    companion object {
        fun newInstance() = OpcionFragment()
    }

    private lateinit var viewModel: OpcionViewModel
    private lateinit var baseDatos: FirebaseDatabase

    // TODO: Referenciar datos obtenidos del fragmento anterior
    private val args : OpcionFragmentArgs by navArgs<OpcionFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.opcion_fragment, container, false)
        baseDatos = FirebaseDatabase.getInstance()
        setupExternalData(view)
        return view
    }

    private fun setupExternalData(view: View?) {
        val usuario = FirebaseAuth.getInstance().currentUser
        val referencia = baseDatos.getReference("Cliente/${usuario?.uid}/dieta/0/comidas/0/opciones/")
        val alimentos = view?.findViewById<TextView>(R.id.alimentos)
        val bebidas = view?.findViewById<TextView>(R.id.bebidas)

        // TODO: Get info from DB
        referencia.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (c in snapshot.children) {
                    println("Dato: RANDom")
                }
                //alimentos?.text = alimento?.alimentos
                //bebidas?.text = alimento?.bebidas
//                println("ALIMENTO: ${alimento}")

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OpcionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}