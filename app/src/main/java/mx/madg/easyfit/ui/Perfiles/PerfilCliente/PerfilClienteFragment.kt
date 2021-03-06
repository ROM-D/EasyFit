package mx.madg.easyfit.ui.Perfiles.PerfilCliente

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.madg.easyfit.ActivityNav
import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.Models.Dieta.Dia
import mx.madg.easyfit.databinding.FragmentPerfilClienteBinding
import mx.madg.easyfit.ui.login.LoginActivity


class PerfilClienteFragment : Fragment() {

    private lateinit var perfilClienteViewModel: PerfilClienteViewModel
    private var _binding: FragmentPerfilClienteBinding? = null
    private lateinit var dietas: ArrayList<Dia>  // TODO: Retrieve dieta from user

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        perfilClienteViewModel = ViewModelProvider(this).get(PerfilClienteViewModel::class.java)

        _binding = FragmentPerfilClienteBinding.inflate(inflater, container, false)
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

        //Clientes
        val referencia = baseDatos.getReference("Clientes/${usuario?.uid}/")
        //println("LA REFERENCIA: ${referencia}")

        //Se actualiza cada vez que me llegue un dato
        referencia.addValueEventListener(object: ValueEventListener { //Este me da avisos o actualizaciones en tiempo real
            override fun onDataChange(snapshot: DataSnapshot) {
                println("LA REFERENCIA: ${referencia}")
                println("EL SNAPSHOT: ${snapshot}")
                val cliente = snapshot.getValue(Cliente::class.java)

                dietas = cliente?.dieta!!

                binding.tvNombre.setText(cliente?.nombre.toString())
                val id = "id: " + cliente?.id.toString()
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
            FirebaseAuth.getInstance().signOut()
            // startActivity(Intent(activity, ActivityNav::class.java))
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        binding.btnActualizarPerfil.setOnClickListener{
            ActualizarDatos()
        }
    }

    private fun ActualizarDatos() {
        val baseDatos = Firebase.database
        val usuario = FirebaseAuth.getInstance().currentUser
        val altura = binding.tvAltura.text.toString()
        val Peso = binding.tvPeso.text.toString()
        val PorcentajeGrasa = binding.tvPorcentajeGrasa.text.toString()
        val PorcentajeMusculo = binding.tvPorcentajeMusculo.text.toString()

        if(altura!="." && Peso!="." && PorcentajeGrasa!="."&& PorcentajeMusculo!="." && altura!="" && Peso!="" && PorcentajeGrasa!=""&& PorcentajeMusculo!=""){
            val referencia = baseDatos.getReference("Clientes/${usuario?.uid}")
            //val id = baseDatos.getReference("Clientes/${usuario?.uid}/id").get()
            val valor = Cliente(
                usuario?.uid.toString(),
                binding.tvNombre.text.toString(),
                binding.tvEmail.text.toString(),
                dietas,
                binding.tvPeso.text.toString().toDouble(),
                binding.tvAltura.text.toString().toDouble(),
                binding.tvSexo.text.toString(),
                binding.tvPorcentajeMusculo.text.toString().toDouble(),
                binding.tvPorcentajeGrasa.text.toString().toDouble(),
                0,
            )
            referencia.setValue(valor)

        }else{
            Toast.makeText(context,"Introduce un valor valido", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}