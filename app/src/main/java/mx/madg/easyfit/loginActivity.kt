package mx.madg.easyfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import mx.madg.easyfit.databinding.ActivityLoginBinding

class loginActivity : AppCompatActivity() {

    private val CODIGO_SIGNIN: Int = 500

    private lateinit var binding: ActivityLoginBinding

    private lateinit var baseDatos: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseDatos = FirebaseDatabase.getInstance()
        configurarEventos()
    }

    override fun onStart() {
        super.onStart()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODIGO_SIGNIN) {
            when (resultCode) {
                RESULT_OK -> {
                    //La instancia es un objeto que administra la autentificacion
                    val usuario = FirebaseAuth.getInstance().currentUser
                    // Guardar los datos si es la primera vez, creo...

                    // Lanzar otra actividad
                    startActivity(Intent(this,ActivityNav::class.java))
                }

                RESULT_CANCELED -> {
                    //val response = IdpResponse.fromResultIntent(data)
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //val response = IdpResponse.fromResultIntent(data)
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                } }

        }

    }
}