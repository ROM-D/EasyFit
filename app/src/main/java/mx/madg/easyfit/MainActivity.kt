package mx.madg.easyfit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast


import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import mx.madg.easyfit.databinding.ActivityLoginBinding
import mx.madg.easyfit.ui.Cliente

class MainActivity : AppCompatActivity() {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding
    private lateinit var bindingLogin: ActivityLoginBinding
    private lateinit var baseDatos: FirebaseDatabase
    private val CODIGO_SIGNIN: Int = 500
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)

        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)

        baseDatos = FirebaseDatabase.getInstance()
        configurarEventos()
    }

    override fun onStart() {
        super.onStart()
        val usuario = mAuth.currentUser
        if(usuario != null){
            val view = Intent(this,ActivityNav::class.java)
            startActivity(view)
        }
    }

    private fun configurarEventos() {
        bindingLogin.btnGoogleSignIn.setOnClickListener{
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
                    //Le manda los datos a la BD
                    //La instancia es un objeto que administra la autentificacion
                    val usuario = FirebaseAuth.getInstance().currentUser
                    if(usuario == null){
                        val referencia = baseDatos.getReference("Clientes/${usuario?.uid}/")

                        val cliente = Cliente(usuario?.uid,usuario?.displayName , usuario?.email)
                        referencia.setValue(cliente)
                    }
                    // Lanzar otra actividad
                    val view = Intent(this,ActivityNav::class.java)
                    startActivity(view)
                }

                RESULT_CANCELED -> {
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                } }
        }
    }

}