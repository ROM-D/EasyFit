package mx.madg.easyfit.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.madg.easyfit.R
import mx.madg.easyfit.databinding.FragmentHomeBinding
import mx.madg.easyfit.loginActivity
import mx.madg.easyfit.ui.Cliente

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        //val textView: TextView = binding.textView10
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
           //textView.text = it
       })
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

        //Clientes
        val referencia = baseDatos.getReference("Clientes/${usuario?.uid}")
        //println("LA REFERENCIA: ${referencia}")

        //Se actualiza cada vez que me llegue un dato
        referencia.addValueEventListener(object: ValueEventListener { //Este me da avisos o actualizaciones en tiempo real
            override fun onDataChange(snapshot: DataSnapshot) {
                //println("LA REFERENCIA: ${referencia}")
                //println("EL SNAPSHOT: ${snapshot}")
                val cliente =snapshot.getValue(Cliente::class.java)
                binding.tvNombre.setText(cliente?.nombre.toString())
                val id = "id: "+cliente?.id.toString()
                binding.tvId.setText(id)
                binding.tvEmail.setText(cliente?.mail.toString())
                binding.tvPeso.setText(cliente?.peso.toString())
                binding.tvAltura.setText(cliente?.altura.toString())
                binding.tvSexo.setText(cliente?.sexo.toString())
                binding.tvPorcentajeGrasa.setText(cliente?.porcentajeGrasaCorporal.toString())
                binding.tvPorcentajeMusculo.setText(cliente?.porcentajeDeMusculo.toString())
                binding.imageProfile.setImageURI(usuario?.photoUrl)


            }

            override fun onCancelled(error: DatabaseError) {
                println("Error :(")
            }

        })
    }

    private fun configurarEventos() {
        binding.btnSignout.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext())
            startActivity(Intent(activity,loginActivity::class.java))
        }
        binding.btnActualizarPerfil.setOnClickListener{
            ActualizarDatos()
        }
    }

    private fun ActualizarDatos() {
        val baseDatos = Firebase.database
        val usuario = FirebaseAuth.getInstance().currentUser
        //Se actualiza cada vez que me llegue un dato
        //Clientes
        val referencia = baseDatos.getReference("Clientes/${usuario?.uid}")
        //val id = baseDatos.getReference("Clientes/${usuario?.uid}/id").get()
        val valor = Cliente(
            usuario?.uid.toString(),
            binding.tvNombre.text.toString(),
            binding.tvEmail.text.toString(),
            binding.tvPeso.text.toString().toDouble(),
            binding.tvAltura.text.toString().toDouble(),
            binding.tvSexo.text.toString(),
            binding.tvPorcentajeMusculo.text.toString().toDouble(),
            binding.tvPorcentajeGrasa.text.toString().toDouble(),
        0,
        )
        referencia.setValue(valor)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}