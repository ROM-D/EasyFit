package mx.madg.easyfit

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.madg.easyfit.Models.Nutriologo.Nutriologo
import mx.madg.easyfit.databinding.NutriologoFragmentBinding
import mx.madg.easyfit.ui.login.LoginActivity

class NutriologoFragment : Fragment() {

    private lateinit var perfilNutriologoViewModel: NutriologoViewModel
    private var _binding: NutriologoFragmentBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        perfilNutriologoViewModel = ViewModelProvider(this).get(NutriologoViewModel::class.java)

        _binding = NutriologoFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        configurarEventos()
        return root
    }

    override fun onStart() {
        super.onStart()
        leerDatos()
    }

    private fun leerDatos() {
        val baseDatos = Firebase.database
        val usuario = FirebaseAuth.getInstance().currentUser

        //Entrenadores
        val referencia = baseDatos.getReference("Nutriologos/${usuario?.uid}")
        //Se actualiza cada vez que me llegue un dato
        referencia.addValueEventListener(object: ValueEventListener { //Este me da avisos o actualizaciones en tiempo real
            override fun onDataChange(snapshot: DataSnapshot) {
                //println("LA REFERENCIA: ${referencia}")
                println("***************************EL SNAPSHOT: ${snapshot}")

                val nutriologoEntrenador =snapshot.getValue(Nutriologo::class.java)
                println("***************************EL SNAPSHOT: ${nutriologoEntrenador}")
                binding.tvNombreNutriologo.setText(nutriologoEntrenador?.nombre)
                val id = "id: "+nutriologoEntrenador?.id.toString()
                binding.tvIdNutriologo.setText(id)
                binding.tvEmail.setText(nutriologoEntrenador?.mail.toString())
                binding.tvCelular.setText(nutriologoEntrenador?.celular.toString())
                binding.tvTipoNutriologo.setText("Nutriologo")



            }
            override fun onCancelled(error: DatabaseError) {
                println("Error :(")
            }
        })
    }

    private fun configurarEventos() {
        binding.btnSignout.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext())
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        binding.btnActualizarPerfil.setOnClickListener{
            ActualizarDatos()
        }
    }

    private fun ActualizarDatos() {
        val baseDatos = Firebase.database
        val usuario = FirebaseAuth.getInstance().currentUser
        //Se actualiza cada vez que me llegue un dato
        //Entrenadores
        val referencia = baseDatos.getReference("Nutriologos/${usuario?.uid}")

        val valor = Nutriologo(
            usuario?.uid.toString(),
            binding.tvNombreNutriologo.text.toString(),
            binding.tvEmail.text.toString(),
            binding.tvCelular.text.toString().toLong()
        )

        referencia.setValue(valor)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}