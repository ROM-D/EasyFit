/*package mx.madg.easyfit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.databinding.ActivityLoginBinding

// import mx.madg.easyfit.ui.Cliente


class MainActivity : AppCompatActivity() {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding
    private lateinit var bindingLogin: ActivityLoginBinding
    private lateinit var baseDatos: FirebaseDatabase
    private val CODIGO_SIGNIN: Int = 500
    private val mAuth = FirebaseAuth.getInstance()
    private var tipo:Int = 0
    private var flag:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        bindingLogin.switchNutriologo.setOnCheckedChangeListener { compoundButton, b ->
            tipo = 1
            if(bindingLogin.switchNutriologo.isChecked && bindingLogin.switchEntrenador.isChecked){
                bindingLogin.switchEntrenador.setChecked(false)
            }
        }
        bindingLogin.switchEntrenador.setOnCheckedChangeListener { compoundButton, b ->
            tipo = 2
            if(bindingLogin.switchNutriologo.isChecked && bindingLogin.switchEntrenador.isChecked){
                bindingLogin.switchNutriologo.setChecked(false)
            }
        }
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
                    val referencia = baseDatos.getReference("Clientes/${usuario?.uid}/")
                    if(tipo == 0){
                        val referencia = baseDatos.getReference("Clientes/${usuario?.uid}/")
                        val existing = baseDatos.getReference().child("Clientes").child("${usuario?.uid}")
                        if(existing != null){
                            val cliente = Cliente(usuario!!.uid,usuario?.displayName , usuario?.email)
                            referencia.setValue(cliente)
                            // Lanzar otra actividad
                            val view = Intent(this,ActivityNav::class.java)
                            startActivity(view)
                        }
                        // Lanzar otra actividad
                        val view = Intent(this,ActivityNav::class.java)
                        startActivity(view)
                    }else if(tipo == 1){
                        val referencia = baseDatos.getReference("Nutriologos/${usuario?.uid}/")
                        val existing = baseDatos.getReference().child("Nutriologos").child("${usuario?.uid}")
                        if(existing != null){
                            val cliente = Cliente(usuario!!.uid,usuario?.displayName , usuario?.email)
                            referencia.setValue(cliente)
                            // Lanzar otra actividad
                            val view = Intent(this,ActivityNav::class.java)
                            startActivity(view)
                        }
                        // Lanzar otra actividad
                        val view = Intent(this,ActivityNav::class.java)
                        startActivity(view)
                    }else if(tipo == 2){
                        val referencia = baseDatos.getReference("Entrenadores/${usuario?.uid}/")
                        val existing = baseDatos.getReference().child("Entrenadores").child("${usuario?.uid}")
                        if(existing != null){
                            val cliente = Cliente(usuario!!.uid,usuario?.displayName , usuario?.email)
                            referencia.setValue(cliente)
                            // Lanzar otra actividad
                            val view = Intent(this,ActivityNav::class.java)
                            startActivity(view)
                        }
                        // Lanzar otra actividad
                        val view = Intent(this,ActivityNav::class.java)
                        startActivity(view)
                    }

                    /*val existing = baseDatos.getReference().child("Clientes").child("${usuario?.uid}")
                    if(existing != null){
                        val cliente = Cliente(usuario?.uid,usuario?.displayName , usuario?.email)
                        referencia.setValue(cliente)
                        // Lanzar otra actividad
                        val view = Intent(this,ActivityNav::class.java)
                        startActivity(view)
                    }
                    // Lanzar otra actividad
                    val view = Intent(this,ActivityNav::class.java)
                    startActivity(view)*/
                }

                RESULT_CANCELED -> {
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                } }
        }
    }


       // }
    // }

    private fun configurarEventos() {
        bindingLogin.switchNutriologo.setOnCheckedChangeListener { compoundButton, b ->
            tipo = 1
            if(bindingLogin.switchNutriologo.isChecked && bindingLogin.switchEntrenador.isChecked){
                bindingLogin.switchEntrenador.setChecked(false)
            }
        }
        bindingLogin.switchEntrenador.setOnCheckedChangeListener { compoundButton, b ->
            tipo = 2
            if(bindingLogin.switchNutriologo.isChecked && bindingLogin.switchEntrenador.isChecked){
                bindingLogin.switchNutriologo.setChecked(false)
            }
        }
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
                    val referencia = baseDatos.getReference("Clientes/${usuario?.uid}/")
                    if(tipo == 0){
                        val referencia = baseDatos.getReference("Clientes/${usuario?.uid}/")
                        val existing = baseDatos.getReference().child("Clientes").child("${usuario?.uid}")
                        if(existing != null){
                            val cliente = Cliente(usuario!!.uid,usuario?.displayName , usuario?.email)
                            referencia.setValue(cliente)
                            // Lanzar otra actividad
                            val view = Intent(this,ActivityNav::class.java)
                            startActivity(view)
                        }
                        // Lanzar otra actividad
                        val view = Intent(this,ActivityNav::class.java)
                        startActivity(view)
                    }else if(tipo == 1){
                        val referencia = baseDatos.getReference("Nutriologos/${usuario?.uid}/")
                        val existing = baseDatos.getReference().child("Nutriologos").child("${usuario?.uid}")
                        if(existing != null){
                            val cliente = Cliente(usuario!!.uid,usuario?.displayName , usuario?.email)
                            referencia.setValue(cliente)
                            // Lanzar otra actividad
                            val view = Intent(this,ActivityNav::class.java)
                            startActivity(view)
                        }
                        // Lanzar otra actividad
                        val view = Intent(this,ActivityNav::class.java)
                        startActivity(view)
                    }else if(tipo == 2){
                        val referencia = baseDatos.getReference("Entrenadores/${usuario?.uid}/")
                        val existing = baseDatos.getReference().child("Entrenadores").child("${usuario?.uid}")
                        if(existing != null){
                            val cliente = Cliente(usuario!!.uid,usuario?.displayName , usuario?.email)
                            referencia.setValue(cliente)
                            // Lanzar otra actividad
                            val view = Intent(this,ActivityNav::class.java)
                            startActivity(view)
                        }
                        // Lanzar otra actividad
                        val view = Intent(this,ActivityNav::class.java)
                        startActivity(view)
                    }

                    /*val existing = baseDatos.getReference().child("Clientes").child("${usuario?.uid}")
                    if(existing != null){
                        val cliente = Cliente(usuario?.uid,usuario?.displayName , usuario?.email)
                        referencia.setValue(cliente)
                        // Lanzar otra actividad
                        val view = Intent(this,ActivityNav::class.java)
                        startActivity(view)
                    }
                    // Lanzar otra actividad
                    val view = Intent(this,ActivityNav::class.java)
                    startActivity(view)*/
                }

                RESULT_CANCELED -> {
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                } }
        }
    }


} */