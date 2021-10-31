package mx.madg.easyfit.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import mx.madg.easyfit.MainActivity
import mx.madg.easyfit.R
import mx.madg.easyfit.databinding.NavLoginFragmentBinding

class nav_login : Fragment() {

    companion object {
        fun newInstance() = nav_login()
    }

    private lateinit var baseDatos: FirebaseDatabase
    private lateinit var binding: NavLoginFragmentBinding
    private lateinit var viewModel: NavLoginViewModel
    private val CODIGO_SIGNIN: Int = 500

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.nav_login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NavLoginViewModel::class.java)
        // TODO: Use the ViewModel
        baseDatos = FirebaseDatabase.getInstance()
        //escribirDatos()
        configurarEventos()
    }

    private fun configurarEventos() {
        binding.btnGoogleSignIn.setOnClickListener{
            autentificar()
        }
    }

    private fun autentificar() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        //Quiero empezar una actividad y espero un resultado
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            CODIGO_SIGNIN
        )
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODIGO_SIGNIN) {
            when (resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    //La instancia es un objeto que administra la autentificacion
                    val usuario = FirebaseAuth.getInstance().currentUser
                    // Guardar los datos si es la primera vez, creo...

                    // Lanzar otra actividad
                    startActivity(Intent(this, MainActivity::class.java)) // TODO: check
                }

                AppCompatActivity.RESULT_CANCELED -> {
                    //val response = IdpResponse.fromResultIntent(data)
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //val response = IdpResponse.fromResultIntent(data)
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                } }

        }

    }*/


    private fun escribirDatos() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Administradores/A01748632")

        //myRef.setValue("Oscar Macias Rodriguez")
        //myRef.setValue("Rodrigo Cravioto Caballero")
        //myRef.setValue("Diego Raúl Elizalde Uriarte")
        //myRef.setValue("Michel Antoine Dionne Gutiérrez")
    }

}