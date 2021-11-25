package mx.madg.easyfit.ui.login

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
import mx.madg.easyfit.MainActivity
import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.Models.Dieta.Comida
import mx.madg.easyfit.Models.Dieta.Dia
import mx.madg.easyfit.Models.Dieta.Opcion
import mx.madg.easyfit.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {  // TODO: Check

    private val CODIGO_SIGNIN: Int = 500
    private var tipoAcceso: Int = 0
    private lateinit var binding: ActivityLoginBinding
    private lateinit var baseDatos: FirebaseDatabase
    private lateinit var referencia: DatabaseReference
    private lateinit var tabla: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseDatos = FirebaseDatabase.getInstance()
        configurarEventos()
    }

    override fun onStart() {
        super.onStart()
        val usuario = FirebaseAuth.getInstance().currentUser
        Log.i("EXISTING", "EXISTING: ${tipoAcceso}")
        if(usuario != null) {
            val view = Intent(this, MainActivity::class.java) // TODO: NAVIGATE
            startActivity(view)
        }
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

        binding.btnGoogleSignIn.setOnClickListener {
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
                    Log.i("EXISTING", "EXISTING: ${tipoAcceso}")
                    if (tipoAcceso == 0) {
                        tabla = "Clientes"
                    } else if (tipoAcceso == 1) {
                        tabla = "Nutriologos"
                    } else if (tipoAcceso == 2) {
                        tabla = "Entrenadores"
                    }

                    referencia = baseDatos.getReference("${tabla}/${usuario?.uid}/")

                    val existing = baseDatos.getReference()
                        .child(tabla).child("${usuario?.uid}")

                    Log.i("EXISTING", "EXISTING: ${existing}")

                    if (existing != null) {
                        Log.i("EXISTING","ENTRÉ ******* (`u`)//")

                        // ARRAYS
                        var opcion = Opcion("10g pollo", "licuado de fresa frio")
                        var opciones = arrayListOf<Opcion>()
                        opciones.add(opcion)

                        var comida = Comida("12:30",opciones)
                        var dias = arrayListOf<Comida>()
                        dias.add(comida)

                        var dia = Dia("Lunes", dias)
                        val dieta = arrayListOf<Dia>()
                        dieta.add(dia)

                        comida = Comida("2:30", opciones)
                        dias = arrayListOf<Comida>()
                        dias.add(comida)

                        dia = Dia("Martes", dias)
                        dieta.add(dia)


                        val cliente = Cliente(usuario!!.uid, usuario?.displayName , usuario?.email, dieta)

                        referencia.setValue(cliente)
                        // Lanzar otra actividad
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }

                    // Lanzar otra actividad
                    startActivity(Intent(this, MainActivity::class.java)) // TODO: check
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