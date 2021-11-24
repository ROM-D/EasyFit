package mx.madg.easyfit.ui.login

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mx.madg.easyfit.ActivityNav
import mx.madg.easyfit.MainActivity
import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.R
import mx.madg.easyfit.databinding.NavLoginFragmentBinding
import mx.madg.easyfit.ui.Dieta.Dietas.DietasFragmentDirections


class nav_login : Fragment() {

    companion object {
        fun newInstance() = nav_login()
    }

    private val CODIGO_SIGNIN: Int = 500
    private var tipoAcceso: Int = 0
    private lateinit var baseDatos: FirebaseDatabase
    private lateinit var referencia: DatabaseReference
    private lateinit var tabla: String
    private lateinit var binding: NavLoginFragmentBinding
    //private lateinit var viewModel: NavLoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.nav_login_fragment, container, false)

        baseDatos = FirebaseDatabase.getInstance()
        configurarEventos()
        return view
    }


    /*override fun onStart() {
        super.onStart()
        val usuario = FirebaseAuth.getInstance().currentUser
        if(usuario != null){
            val view = Intent(requireContext(), MainActivity::class.java) // TODO: NAVIGATE
            activity?.startActivity(view)

            Log.i("Login", "LOGGED")
            val accion = nav_loginDirections.actionNavLoginToNavHome()
            findNavController().navigate(accion)
        }
    }*/

    private fun configurarEventos() {
        /*val switchEntrenador = view?.findViewById<Switch>(R.id.switchEntrenador)!!
        val switchNutriologo = view?.findViewById<Switch>(R.id.switchNutriologo)!!

        switchNutriologo.setOnCheckedChangeListener{ compoundButton, b ->
            tipoAcceso = 1
            if(switchEntrenador.isChecked && switchNutriologo.isChecked){
                switchEntrenador.setChecked(false)
            }
        }

        switchEntrenador.setOnCheckedChangeListener{ compoundButton, b ->
            tipoAcceso = 2
            if(switchEntrenador.isChecked && switchNutriologo.isChecked){
                switchNutriologo.setChecked(false)
            }
        }*/

        val btnGoogle = view?.findViewById<SignInButton>(R.id.btnGoogleSignIn)
        btnGoogle?.setOnClickListener {
            print("AUTENTICARÁ")
            autentificar()
        }

    }

    private fun autentificar() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            CODIGO_SIGNIN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODIGO_SIGNIN) {
            when (resultCode) {
                RESULT_OK -> {
                    val usuario = FirebaseAuth.getInstance().currentUser

                    if (tipoAcceso == 0) {
                        tabla = "Clientes"
                    } else if (tipoAcceso == 1) {
                        tabla = "Nutriologos"
                    } else if (tipoAcceso == 2) {
                        tabla = "Entrenadores"
                    }

                    /*referencia = baseDatos.getReference("${tabla}/${usuario?.uid}/")

                    val existing = baseDatos.getReference()
                        .child(tabla).child("${usuario?.uid}")

                    if (existing != null) {
                        val cliente = Cliente(usuario!!.uid, usuario.displayName , usuario.email)
                        referencia.setValue(cliente)
                        // Lanzar otra actividad
                        val intent = Intent(activity?.application, MainActivity::class.java)
                        startActivity(intent)
                    }*/

                    // Lanzar otra actividad
                    Log.i("Login", "LOGGED")
                    val accion = nav_loginDirections.actionNavLoginToNavHome()
                    findNavController().navigate(accion)
                    // val intent = Intent(activity?.application, MainActivity::class.java)
                    // startActivity(intent)
                }

                RESULT_CANCELED -> {
                    //val response = IdpResponse.fromResultIntent(data)
                    Toast.makeText(context, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //val response = IdpResponse.fromResultIntent(data)
                    Toast.makeText(context, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                } }

        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(NavLoginViewModel::class.java)
        // TODO: Use the ViewModel
        //escribirDatos()
    }

    private fun escribirDatos() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Administradores/A01748632")

        //myRef.setValue("Oscar Macias Rodriguez")
        //myRef.setValue("Rodrigo Cravioto Caballero")
        //myRef.setValue("Diego Raúl Elizalde Uriarte")
        //myRef.setValue("Michel Antoine Dionne Gutiérrez")
    }

}