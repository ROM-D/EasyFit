package mx.madg.easyfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.databinding.ActivityNavBinding

class ActivityNav: AppCompatActivity() {

    private val CODIGO_SIGNIN: Int = 500
    private var tipoAcceso: Int = 0
    private lateinit var tabla: String
    private lateinit var binding: ActivityNavBinding
    private lateinit var baseDatos: FirebaseDatabase
    private lateinit var referencia: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseDatos = FirebaseDatabase.getInstance()
        configurarEventos()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun configurarEventos() {
        val switchEntrenador = binding.switchEntrenador
        val switchNutriologo = binding.switchNutriologo

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
        }

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

                    /*if (tipoAcceso == 0) {
                        tabla = "Clientes"
                    } else if (tipoAcceso == 1) {
                        tabla = "Nutriologos"
                    } else if (tipoAcceso == 2) {
                        tabla = "Entrenadores"
                    }

                    referencia = baseDatos.getReference("${tabla}/${usuario?.uid}/")

                    val existing = baseDatos.getReference()
                        .child(tabla).child("${usuario?.uid}")


                    if (existing != null) {
                        val cliente = Cliente(usuario!!.uid, usuario?.displayName , usuario?.email)
                        referencia.setValue(cliente)
                        // Lanzar otra actividad
                        // val intent = Intent(this, MainActivity::class.java)
                        // startActivity(intent)

                        // Lanzar otra actividad
                        startActivity(Intent(this, MainActivity::class.java))
                    }*/

                    // Lanzar otra actividad
                    startActivity(Intent(this, MainActivity::class.java))
                }

                RESULT_CANCELED -> {
                    //val response = IdpResponse.fromResultIntent(data)
                    Toast.makeText(this, "No se ha podido iniciar sesi??n", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //val response = IdpResponse.fromResultIntent(data)
                    Toast.makeText(this, "No se ha podido iniciar sesi??n", Toast.LENGTH_SHORT).show()
                } }

        }

    }

}